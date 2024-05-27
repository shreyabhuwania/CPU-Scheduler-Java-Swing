import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoundRobin extends CPUScheduler {
    
    @Override
    public void process() {
        // Sort the list of rows based on arrival time
        Collections.sort(this.getRows(), (o1, o2) -> Integer.compare(((Row) o1).getArrivalTime(), ((Row) o2).getArrivalTime()));

        // Create a deep copy of the list of rows
        List<Row> rows = Utility.deepCopy(this.getRows());

        int time = rows.get(0).getArrivalTime(); // Current time
        int timeQuantum = this.getTimeQuantum(); // Time quantum

        // Execute until all rows are processed
        while (!rows.isEmpty()) {
            Row row = rows.get(0); // Get the first row
            int bt = (row.getBurstTime() < timeQuantum ? row.getBurstTime() : timeQuantum); // Remaining burst time

            // Add event to timeline
            this.getTimeline().add(new Event(row.getProcessName(), time, time + bt));
            time += bt; // Update time

            // Update burst time and reinsert the row if needed
            if (row.getBurstTime() > timeQuantum) {
                row.setBurstTime(row.getBurstTime() - timeQuantum); // Update burst time
                rows.remove(0); // Remove from the beginning
                int insertIndex = 0;

                // Find the correct position to reinsert the row
                for (int i = 0; i < rows.size(); i++) {
                    if (rows.get(i).getArrivalTime() > time) {
                        insertIndex = i;
                        break;
                    } else if (i == rows.size() - 1) {
                        insertIndex = rows.size();
                    }
                }
                rows.add(insertIndex, row); // Reinsert the row
            } else {
                rows.remove(0); // Remove if the process completes
            }
        }

        // Calculate waiting and turnaround times
        Map<String, Integer> map = new HashMap<>();
        for (Row row : this.getRows()) {
            map.clear(); // Clear the map
            int waitingTime = 0;

            // Calculate waiting time for the process
            for (Event event : this.getTimeline()) {
                if (event.getProcessName().equals(row.getProcessName())) {
                    if (map.containsKey(event.getProcessName())) {
                        int w = event.getStartTime() - map.get(event.getProcessName());
                        waitingTime += w; // Accumulate waiting time
                    } else {
                        waitingTime = event.getStartTime() - row.getArrivalTime();
                    }
                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }

            // Set waiting and turnaround times for the process
            row.setWaitingTime(waitingTime);
            row.setTurnaroundTime(waitingTime + row.getBurstTime());
        }
    }
}
