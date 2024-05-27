import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShortestRemainingTime extends CPUScheduler {
    @Override
    public void process() {
        // Sort rows based on arrival time
        Collections.sort(this.getRows(), (o1, o2) -> {
            return Integer.compare(((Row) o1).getArrivalTime(), ((Row) o2).getArrivalTime());
        });

        List<Row> rows = Utility.deepCopy(this.getRows());
        int time = rows.get(0).getArrivalTime();

        while (!rows.isEmpty()) {
            List<Row> availableRows = new ArrayList<>();

            // Select available rows based on arrival time
            for (Row row : rows) {
                if (row.getArrivalTime() <= time) {
                    availableRows.add(row);
                }
            }

            // Sort availableRows based on burst time
            Collections.sort(availableRows, (o1, o2) -> {
                return Integer.compare(((Row) o1).getBurstTime(), ((Row) o2).getBurstTime());
            });

            // Execute the shortest remaining time process
            Row row = availableRows.get(0);
            this.getTimeline().add(new Event(row.getProcessName(), time, ++time));
            row.setBurstTime(row.getBurstTime() - 1);

            // Remove completed processes
            if (row.getBurstTime() == 0) {
                Iterator<Row> iterator = rows.iterator();
                while (iterator.hasNext()) {
                    Row r = iterator.next();
                    if (r.getProcessName().equals(row.getProcessName())) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        // Merge adjacent events with the same process
        for (int i = this.getTimeline().size() - 1; i > 0; i--) {
            List<Event> timeline = this.getTimeline();
            if (timeline.get(i - 1).getProcessName().equals(timeline.get(i).getProcessName())) {
                timeline.get(i - 1).setFinishTime(timeline.get(i).getFinishTime());
                timeline.remove(i);
            }
        }

        // Calculate waiting and turnaround times
        Map<String, Integer> map = new HashMap<>();
        for (Row row : this.getRows()) {
            map.clear();
            int waitingTime = 0;
            for (Event event : this.getTimeline()) {
                if (event.getProcessName().equals(row.getProcessName())) {
                    if (map.containsKey(event.getProcessName())) {
                        int w = event.getStartTime() - map.get(event.getProcessName());
                        waitingTime += w;
                    } else {
                        waitingTime = event.getStartTime() - row.getArrivalTime();
                    }
                    map.put(event.getProcessName(), event.getFinishTime());
                }
            }
            row.setWaitingTime(waitingTime);
            row.setTurnaroundTime(waitingTime + row.getBurstTime());
        }
    }
}
