import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityNonPreemptive extends CPUScheduler {
    @Override
    public void process() {
        
        // Sort the rows based on arrival time
        Collections.sort(this.getRows(), (Object o1, Object o2) -> {
            if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
                return 0;
            } else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
                return -1;
            } else {
                return 1;
            }
        });

        // Create a copy of rows
        List<Row> rows = Utility.deepCopy(this.getRows());
        // Initialize time to the arrival time of the first row
        int time = rows.get(0).getArrivalTime();

        // Continue until all rows are processed
        while (!rows.isEmpty()) {
            // Find the rows that have arrived by the current time
            List<Row> availableRows = new ArrayList<>();
            for (Row row : rows) {
                if (row.getArrivalTime() <= time) {
                    availableRows.add(row);
                }
            }

            // Sort the available rows based on priority level
            Collections.sort(availableRows, (Object o1, Object o2) -> {
                if (((Row) o1).getPriorityLevel() == ((Row) o2).getPriorityLevel()) {
                    return 0;
                } else if (((Row) o1).getPriorityLevel() < ((Row) o2).getPriorityLevel()) {
                    return -1;
                } else {
                    return 1;
                }
            });

            // Get the row with the highest priority
            Row row = availableRows.get(0);
            // Add the event to the timeline and update time
            this.getTimeline().add(new Event(row.getProcessName(), time, time + row.getBurstTime()));
            time += row.getBurstTime();

            // Remove the processed row from the list
            for (int i = 0; i < rows.size(); i++) {
                if (rows.get(i).getProcessName().equals(row.getProcessName())) {
                    rows.remove(i);
                    break;
                }
            }
        }

        // Calculate waiting time and turnaround time for each row
        for (Row row : this.getRows()) {
            // Get the event associated with the row
            Event event = this.getEvent(row);
            if (event != null) {
                // If event exists, calculate waiting time and turnaround time
                row.setWaitingTime(event.getStartTime() - row.getArrivalTime());
                row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
            } else {
                // If no event found, set waiting time to 0 and turnaround time to burst time
                row.setWaitingTime(0);
                row.setTurnaroundTime(row.getBurstTime());
            }
        }
    }
}
