import java.util.Collections;
import java.util.List;

public class FirstComeFirstServe extends CPUScheduler {

    @Override
    public void process() {
        // Sort the list of rows based on arrival time using Collections.sort
        Collections.sort(this.getRows(), (o1, o2) -> {
            if (((Row) o1).getArrivalTime() == ((Row) o2).getArrivalTime()) {
                return 0;
            } else if (((Row) o1).getArrivalTime() < ((Row) o2).getArrivalTime()) {
                return -1;
            } else {
                return 1;
            }
        });
        
        // Get the timeline from the base class
        List<Event> timeline = this.getTimeline();
        
        // Create events for each row and add them to the timeline
        for (Row row : this.getRows()) {
            if (timeline.isEmpty()) {
                // If timeline is empty, create an event starting at arrival time and ending at arrival time + burst time
                timeline.add(new Event(row.getProcessName(), row.getArrivalTime(), row.getArrivalTime() + row.getBurstTime()));
            } else {
                // If timeline is not empty, get the last event and create a new event starting at the last event's finish time
                timeline.add(new Event(row.getProcessName(), timeline.get(timeline.size() - 1).getFinishTime(), timeline.get(timeline.size() - 1).getFinishTime() + row.getBurstTime()));
            }
        }
        
        // Calculate waiting time and turnaround time for each row
        for (Row row : this.getRows()) {
            row.setWaitingTime(this.getEvent(row).getStartTime() - row.getArrivalTime());
            row.setTurnaroundTime(row.getWaitingTime() + row.getBurstTime());
        }
    }
}
