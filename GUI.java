import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GUI
{
    private JFrame frame;
    private JPanel mainPanel;
    private CustomPanel chartPanel;
    private JScrollPane tablePane;
    private JScrollPane chartPane;
    private JTable table;
    private JButton addBtn;
    private JButton removeBtn;
    private JButton computeBtn;
    private JLabel wtLabel;
    private JLabel wtResultLabel;
    private JLabel tatLabel;
    private JLabel tatResultLabel;
    private JComboBox<String> option;
    private DefaultTableModel model;
    
    public GUI()
    {
        // Initialize the table model with column headers
        model = new DefaultTableModel(new String[]{"Process", "AT", "BT", "Priority", "WT", "TAT"}, 0);
        
        // Create the table and set it to fill the viewport height
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        tablePane = new JScrollPane(table);
        tablePane.setBounds(25, 25, 450, 250);
        
        // Create buttons for adding and removing rows
        addBtn = new JButton("Add");
        addBtn.setBounds(300, 280, 85, 25);
        addBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        addBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new String[]{"", "", "", "", "", ""}); // Add a new row with empty values
            } 
        });
        
        removeBtn = new JButton("Remove");
        removeBtn.setBounds(390, 280, 85, 25);
        removeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        removeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow(); // Get the selected row index
                
                if (row > -1) {
                    model.removeRow(row); // Remove the selected row
                }
            }
        });
        
        // Create a panel for displaying the chart
        chartPanel = new CustomPanel();
        chartPanel.setBackground(Color.WHITE);
        chartPane = new JScrollPane(chartPanel);
        chartPane.setBounds(25, 310, 450, 100);
        
        // Labels for displaying average waiting time and average turnaround time
        wtLabel = new JLabel("Average Waiting Time:");
        wtLabel.setBounds(25, 425, 180, 25);
        tatLabel = new JLabel("Average Turn Around Time:");
        tatLabel.setBounds(25, 450, 180, 25);
        wtResultLabel = new JLabel();
        wtResultLabel.setBounds(215, 425, 180, 25);
        tatResultLabel = new JLabel();
        tatResultLabel.setBounds(215, 450, 180, 25);
        
        // Combo box for selecting scheduling algorithm
        option = new JComboBox<>(new String[]{"FCFS", "SJF", "SRT", "PSN", "PSP", "RR"});
        option.setBounds(390, 420, 85, 20);
        
        // Button for computing scheduling and updating the results
        computeBtn = new JButton("Compute");
        computeBtn.setBounds(390, 450, 85, 25);
        computeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        computeBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) option.getSelectedItem(); // Get the selected algorithm
                
                // Create the scheduler based on the selected algorithm
                CPUScheduler scheduler;
                switch (selected) {
                    case "FCFS":
                        scheduler = new FirstComeFirstServe();
                        break;
                    case "SJF":
                        scheduler = new ShortestJobFirst();
                        break;
                    case "SRT":
                        scheduler = new ShortestRemainingTime();
                        break;
                    case "PSN":
                        scheduler = new PriorityNonPreemptive();
                        break;
                    case "PSP":
                        scheduler = new PriorityPreemptive();
                        break;
                    case "RR":
                        String tq = JOptionPane.showInputDialog("Time Quantum"); // Prompt for time quantum
                        if (tq == null) {
                            return; // If canceled, return
                        }
                        scheduler = new RoundRobin();
                        scheduler.setTimeQuantum(Integer.parseInt(tq)); // Set the time quantum
                        break;
                    default:
                        return; // If no algorithm selected, return
                }
                
                // Add processes to the scheduler
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    String process = (String) model.getValueAt(i, 0);
                    int at = Integer.parseInt((String) model.getValueAt(i, 1));
                    int bt = Integer.parseInt((String) model.getValueAt(i, 2));
                    int pl;
                    
                    if (selected.equals("PSN") || selected.equals("PSP"))
                    {
                        if (!model.getValueAt(i, 3).equals(""))
                        {
                            pl = Integer.parseInt((String) model.getValueAt(i, 3));
                        }
                        else
                        {
                            pl = 1;
                        }
                    }
                    else
                    {
                        pl = 1;
                    }
                    
                    // Add the process to the scheduler
                    scheduler.add(new Row(process, at, bt, pl));
                }
                
                // Process the scheduling
                scheduler.process();
                
                // Update the table with waiting time and turnaround time for each process
                for (int i = 0; i < model.getRowCount(); i++)
                {
                    String process = (String) model.getValueAt(i, 0);
                    Row row = scheduler.getRow(process);
                    model.setValueAt(row.getWaitingTime(), i, 4);
                    model.setValueAt(row.getTurnaroundTime(), i, 5);
                }
                
                // Display the average waiting time and average turnaround time
                wtResultLabel.setText(Double.toString(scheduler.getAverageWaitingTime()));
                tatResultLabel.setText(Double.toString(scheduler.getAverageTurnAroundTime()));
                
                // Update the chart panel with the scheduling timeline
                chartPanel.setTimeline(scheduler.getTimeline());
            }
        });
        
        // Create the main panel and add components to it
        mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(500, 500));
        mainPanel.add(tablePane);
        mainPanel.add(addBtn);
        mainPanel.add(removeBtn);
        mainPanel.add(chartPane);
        mainPanel.add(wtLabel);
        mainPanel.add(tatLabel);
        mainPanel.add(wtResultLabel);
        mainPanel.add(tatResultLabel);
        mainPanel.add(option);
        mainPanel.add(computeBtn);
        
        // Create the frame and add the main panel to it
        frame = new JFrame("CPU Scheduler Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(mainPanel);
        frame.pack();
    }
    
    public static void main(String[] args)
    {
        new GUI();
    }
    
    // Custom panel for displaying the scheduling timeline
    class CustomPanel extends JPanel
    {   
        private List<Event> timeline;
        
        // Override the paintComponent method to draw the timeline
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            // Draw the timeline if it's not null
            if (timeline != null)
            {
//                int width = 30;
                
                for (int i = 0; i < timeline.size(); i++)
                {
                    Event event = timeline.get(i);
                    int x = 30 * (i + 1);
                    int y = 20;
                    
                    g.drawRect(x, y, 30, 30);
                    g.setFont(new Font("Segoe UI", Font.BOLD, 13));
                    g.drawString(event.getProcessName(), x + 10, y + 20);
                    g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                    g.drawString(Integer.toString(event.getStartTime()), x - 5, y + 45);
                    
                    if (i == timeline.size() - 1)
                    {
                        g.drawString(Integer.toString(event.getFinishTime()), x + 27, y + 45);
                    }
                    
//                    width += 30;
                }
                
//                this.setPreferredSize(new Dimension(width, 75));
            }
        }
        
        // Method to set the timeline and repaint the panel
        public void setTimeline(List<Event> timeline)
        {
            this.timeline = timeline;
            repaint();
        }
    }
}
