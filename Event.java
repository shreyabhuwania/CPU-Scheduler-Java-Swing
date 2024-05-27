public class Event {
    // Member variables to hold event details
    private final String processName;  // Name of the process
    private final int startTime;       // Start time of the event
    private int finishTime;            // Finish time of the event

    // Constructor to initialize an Event object
    public Event(String processName, int startTime, int finishTime) {
        this.processName = processName;    // Set the process name
        this.startTime = startTime;        // Set the start time
        this.finishTime = finishTime;      // Set the finish time
    }

    // Getter method for process name
    public String getProcessName() {
        return processName;
    }

    // Getter method for start time
    public int getStartTime() {
        return startTime;
    }

    // Getter method for finish time
    public int getFinishTime() {
        return finishTime;
    }

    // Setter method for finish time
    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}
