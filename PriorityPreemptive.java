import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriorityPreemptive extends CPUScheduler
{
    @Override
    public void process()
    {
        // Sort the list of processes based on arrival time
        Collections.sort(this.getRows(), (Object o1, Object o2) -> {
            if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime())
            {
                return 0;
            }
            else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        });
        
        // Create a copy of the list of processes
        List<Row> rows = Utility.deepCopy(this.getRows());
        // Initialize time to the arrival time of the first process
        int time = rows.get(0).getArrivalTime();
        
        // Process until all rows are processed
        while (!rows.isEmpty())
        {
            // Create a list to hold available processes
            List<Row> availableRows = new ArrayList<>();
            
            // Check for processes that have arrived by the current time
            for (Row row : rows)
            {
                if (row.getArrivalTime() <= time)
                {
                    availableRows.add(row);
                }
            }
            
            // Sort available processes based on priority level
            Collections.sort(availableRows, (Object o1, Object o2) -> {
                if (((Row) o1).getPriorityLevel() == ((Row) o2).getPriorityLevel())
                {
                    return 0;
                }
                else if (((Row) o1).getPriorityLevel() < ((Row) o2).getPriorityLevel())
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            });
            
            // Get the process with the highest priority
            Row row = availableRows.get(0);
            // Add an event to the timeline for the selected process
            this.getTimeline().add(new Event(row.getProcessName(), time, ++time));
            // Decrease the burst time of the selected process
            row.setBurstTime(row.getBurstTime() - 1);
            
            // Remove the process if its burst time is finished
            if (row.getBurstTime() == 0)
            {
                for (int i = 0; i < rows.size(); i++)
                {
                    if (rows.get(i).getProcessName().equals(row.getProcessName()))
                    {
                        rows.remove(i);
                        break;
                    }
                }
            }
        }
        
        // Merge adjacent events with the same process name in the timeline
        for (int i = this.getTimeline().size() - 1; i > 0; i--)
        {
            List<Event> timeline = this.getTimeline();
            
            if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName()))
            {
                timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());
                timeline.remove(i);
            }
        }
        
        // Calculate waiting and turnaround times for each process
        Map<String, Integer> map = new HashMap<>();
        for (Row row : this.getRows())
        {
            map.clear();
            
            for (Event event : this.getTimeline())
            {
                if (event.getProcessName().equals(row.getProcessName()))
                {
                    if (map.containsKey(event.getProcessName()))
                    {
                        int w = event.getStartTime() - map.get(event.getProcessName());
                        row.setWaitingTime(row.getWaitingTime() + w);
                    }
                    else
                    {
                        row.setWaitingTime(event.getStartTime() - row.getArrivalTime());
                    }
                    
                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }
            
            row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
