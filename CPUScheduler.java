import java.util.ArrayList;
import java.util.List;

// Abstract class for CPU scheduling algorithms
public abstract class CPUScheduler {
    
    // Define lists to store rows and timeline events
    private final List<Row> rows; // List of rows representing processes
    private final List<Event> timeline; // List of timeline events
    private int timeQuantum; // Time quantum for Round Robin scheduling

    // Constructor
    public CPUScheduler() {
        rows = new ArrayList<>(); // Initialize the rows list
        timeline = new ArrayList<>(); // Initialize the timeline list
        timeQuantum = 1; // Default time quantum
    }

    // Method to add a row to the list of rows
    public boolean add(Row row) {
        return rows.add(row);
    }

    // Setter for time quantum
    public void setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    // Getter for time quantum
    public int getTimeQuantum() {
        return timeQuantum;
    }

    // Method to calculate the average waiting time
    public double getAverageWaitingTime() {
        double avg = 0.0;

        for (Row row : rows) {
            avg += row.getWaitingTime();
        }

        return avg / rows.size();
    }

    // Method to calculate the average turnaround time
    public double getAverageTurnAroundTime() {
        double avg = 0.0;

        for (Row row : rows) {
            avg += row.getTurnaroundTime();
        }

        return avg / rows.size();
    }

    // Method to get the event associated with a row
    public Event getEvent(Row row) {
        for (Event event : timeline) {
            if (row.getProcessName().equals(event.getProcessName())) {
                return event;
            }
        }

        return null;
    }

    // Method to get the row by process name
    public Row getRow(String process) {
        for (Row row : rows) {
            if (row.getProcessName().equals(process)) {
                return row;
            }
        }

        return null;
    }

    // Getter for the list of rows
    public List<Row> getRows() {
        return rows;
    }

    // Getter for the timeline of events
    public List<Event> getTimeline() {
        return timeline;
    }

    // Abstract method to be implemented by subclasses for processing
    public abstract void process();
}
