import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("-----------------FCFS----------------");
        fcfs();
        System.out.println("-----------------SJF-----------------");
        sjf();
        System.out.println("-----------------SRT-----------------");
        srt();
        System.out.println("-----------------PSN-----------------");
        psn();
        System.out.println("-----------------PSP-----------------");
        psp();
        System.out.println("-----------------RR------------------");
        rr();
    }

    // Method to demonstrate First-Come, First-Serve (FCFS) scheduling
    public static void fcfs() {
        CPUScheduler fcfs = new FirstComeFirstServe();
        // Adding processes with their arrival time (AT) and burst time (BT)
        fcfs.add(new Row("P1", 0, 5));
        fcfs.add(new Row("P2", 2, 4));
        fcfs.add(new Row("P3", 4, 3));
        fcfs.add(new Row("P4", 6, 6));
        // Processing the scheduling
        fcfs.process();
        // Display the results
        display(fcfs);
    }

    // Method to demonstrate Shortest Job First (SJF) scheduling
    public static void sjf() {
        CPUScheduler sjf = new ShortestJobFirst();
        // Adding processes with their arrival time (AT) and burst time (BT)
        sjf.add(new Row("P1", 0, 5));
        sjf.add(new Row("P2", 2, 3));
        sjf.add(new Row("P3", 4, 2));
        sjf.add(new Row("P4", 6, 4));
        sjf.add(new Row("P5", 7, 1));
        // Processing the scheduling
        sjf.process();
        // Display the results
        display(sjf);
    }

    // Method to demonstrate Shortest Remaining Time (SRT) scheduling
    public static void srt() {
        CPUScheduler srt = new ShortestRemainingTime();
        // Adding processes with their arrival time (AT) and burst time (BT)
        srt.add(new Row("P1", 8, 1));
        srt.add(new Row("P2", 5, 1));
        srt.add(new Row("P3", 2, 7));
        srt.add(new Row("P4", 4, 3));
        srt.add(new Row("P5", 2, 8));
        srt.add(new Row("P6", 4, 2));
        srt.add(new Row("P7", 3, 5));
        // Processing the scheduling
        srt.process();
        // Display the results
        display(srt);
    }

    // Method to demonstrate Priority Non-Preemptive (PSN) scheduling
    public static void psn() {
        CPUScheduler psn = new PriorityNonPreemptive();
        // Adding processes with their arrival time (AT), burst time (BT), and priority
        psn.add(new Row("P1", 8, 1));
        psn.add(new Row("P2", 5, 1));
        psn.add(new Row("P3", 2, 7));
        psn.add(new Row("P4", 4, 3));
        psn.add(new Row("P5", 2, 8));
        psn.add(new Row("P6", 4, 2));
        psn.add(new Row("P7", 3, 5));
        // Processing the scheduling
        psn.process();
        // Display the results
        display(psn);
    }

    // Method to demonstrate Priority Preemptive (PSP) scheduling
    public static void psp() {
        CPUScheduler psp = new PriorityPreemptive();
        // Adding processes with their arrival time (AT), burst time (BT), and priority
        psp.add(new Row("P1", 8, 1));
        psp.add(new Row("P2", 5, 1));
        psp.add(new Row("P3", 2, 7));
        psp.add(new Row("P4", 4, 3));
        psp.add(new Row("P5", 2, 8));
        psp.add(new Row("P6", 4, 2));
        psp.add(new Row("P7", 3, 5));
        // Processing the scheduling
        psp.process();
        // Display the results
        display(psp);
    }

    // Method to demonstrate Round Robin (RR) scheduling
    public static void rr() {
        CPUScheduler rr = new RoundRobin();
        rr.setTimeQuantum(2); // Setting time quantum for Round Robin scheduling
        // Adding processes with their arrival time (AT) and burst time (BT)
        rr.add(new Row("P1", 0, 4));
        rr.add(new Row("P2", 1, 5));
        rr.add(new Row("P3", 2, 6));
        rr.add(new Row("P4", 4, 1));
        rr.add(new Row("P5", 6, 3));
        rr.add(new Row("P6", 7, 2));
        // Processing the scheduling
        rr.process();
        // Display the results
        display(rr);
    }

    // Method to display the results of the scheduling
    public static void display(CPUScheduler object) {
        System.out.println("Process\tAT\tBT\tWT\tTAT");

        // Display each process with its Arrival Time (AT), Burst Time (BT), Waiting Time (WT), and Turnaround Time (TAT)
        for (Row row : object.getRows()) {
            System.out.println(row.getProcessName() + "\t" + row.getArrivalTime() + "\t" + row.getBurstTime() + "\t" + row.getWaitingTime() + "\t" + row.getTurnaroundTime());
        }
        
        System.out.println();
        
        // Display the timeline of the scheduling
        for (int i = 0; i < object.getTimeline().size(); i++) {
            List<Event> timeline = object.getTimeline();
            System.out.print(timeline.get(i).getStartTime() + "(" + timeline.get(i).getProcessName() + ")");
            
            if (i == object.getTimeline().size() - 1) {
                System.out.print(timeline.get(i).getFinishTime());
            }
        }
        
        // Display the average Waiting Time (WT) and Turnaround Time (TAT)
        System.out.println("\n\nAverage WT: " + object.getAverageWaitingTime() + "\nAverage TAT: " + object.getAverageTurnAroundTime());
    }
}
