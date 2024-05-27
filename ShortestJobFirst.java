import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ShortestJobFirst extends CPUScheduler {
    
    @Override
    public void process() {
        // Sort the list of rows based on arrival time
        Collections.sort(this.getRows(), (o1, o2) -> Integer.compare(((Row) o1).getArrivalTime(), ((Row) o2).getArrivalTime()));

        // Create a deep copy of the list of rows
        List<Row> rows = Utility.deepCopy(this.getRows());
        int time = rows.get(0).getArrivalTime(); // Current time

        // Execute until all rows are processed
        while (!rows.isEmpty()) {
            List<Row> availableRows = new ArrayList<>();

            // Collect all rows that have arrived by the current time
            for (Row row : rows) {
                if (row.getArrivalTime() <= time) {
                    availableRows.add(row);
                }
            }

            if (availableRows.isEmpty()) {
                time = rows.get(0).getArrivalTime();
                continue; // No available processes, skip to the next iteration
            }

            // Sort available rows by burst time (SJF)
            Collections.sort(availableRows, (o1, o2) -> Integer.compare(((Row) o1).getBurstTime(), ((Row) o2).getBurstTime()));

            Row row = availableRows.get(0); // Get the shortest job
            this.getTimeline().add(new Event(row.getProcessName(), time, time + row.getBurstTime())); // Add event to timeline
            time += row.getBurstTime(); // Update time

            // Remove the processed row from the list of rows
            Iterator<Row> iterator = rows.iterator();
            while (iterator.hasNext()) {
                Row r = iterator.next();
                if (r.getProcessName().equals(row.getProcessName())) {
                    iterator.remove();
                    break;
                }
            }
        }

        // Calculate waiting and turnaround times for each row
        for (Row row : this.getRows()) {
            Event event = this.getEvent(row);
            if (event != null) {
                // Set waiting time and turnaround time based on the event found
                row.setWaitingTime(event.getStartTime() - row.getArrivalTime());
                row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
            } else {
                // Handle the case where the event for the row is not found
                row.setWaitingTime(0); // Set waiting time to 0
                row.setTurnaroundTime(row.getBurstTime()); // Turnaround time is just the burst time
            }
        }
    }
}
