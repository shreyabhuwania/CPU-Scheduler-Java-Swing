
# CPU Scheduler Simulator with Java GUI

This repository contains a CPU scheduler simulator implemented in Java with a graphical user interface (GUI). The simulator supports various scheduling algorithms such as First Come First Serve (FCFS), Shortest Job First (SJF), Shortest Remaining Time (SRT), Priority Scheduling (Preemptive and Non-Preemptive), and Round Robin (RR).

## Features

- **Add and Remove Processes:** Users can add new processes to the scheduler and remove existing ones.
- **Compute Scheduling:** Users can select a scheduling algorithm and compute the scheduling to visualize the timeline.
- **Visualize Timeline:** The simulator displays a graphical representation of the scheduling timeline using a custom JPanel, depicted as a Gantt chart.
- **Display Metrics:** Average waiting time and average turnaround time are displayed after computing the scheduling.

## Supported Algorithms

- **FCFS (First Come First Serve):** Processes are executed in the order they arrive.
- **SJF (Shortest Job First):** Shortest burst time processes are executed first.
- **SRT (Shortest Remaining Time):** The process with the shortest remaining time to completion is executed next.
- **PSN (Priority Scheduling - Non-Preemptive):** Processes with higher priority levels are executed first without preempting ongoing processes.
- **PSP (Priority Scheduling - Preemptive):** Processes with higher priority levels can preempt ongoing processes.
- **RR (Round Robin):** Each process is assigned a fixed time quantum and executed in a circular queue.

## Usage

1. **Add Processes:** Click the "Add" button to add new rows to the table. Enter process details including process name, arrival time, burst time, and priority (if applicable).
2. **Remove Processes:** Select a row and click "Remove" to delete the selected process.
3. **Compute Scheduling:** Select a scheduling algorithm from the dropdown list and click "Compute". For Round Robin, you will be prompted to enter the time quantum.
4. **View Results:** After computing, the table will be updated with waiting time and turnaround time for each process. The average waiting time and average turnaround time will be displayed below the chart.

## Screenshots

![Screenshot (64)](https://github.com/shreyabhuwania/CPU-Scheduler-Java-Swing/assets/95756422/e889e8c2-9991-42c2-885b-46de9b10dd6c)
![Screenshot (65)](https://github.com/shreyabhuwania/CPU-Scheduler-Java-Swing/assets/95756422/74783db8-7eca-4545-a039-e585b8d3a18d)
![Screenshot (66)](https://github.com/shreyabhuwania/CPU-Scheduler-Java-Swing/assets/95756422/8ccd4bf8-b439-4f28-a514-1e8b7e64edb7)
![Screenshot (67)](https://github.com/shreyabhuwania/CPU-Scheduler-Java-Swing/assets/95756422/a60cc83d-2dbd-4ce9-b0fc-c3fcab8d2c08)
![Screenshot (68)](https://github.com/shreyabhuwania/CPU-Scheduler-Java-Swing/assets/95756422/b47c88c2-8c53-47c6-b4e5-c612845f1d91)
![Screenshot (69)](https://github.com/shreyabhuwania/CPU-Scheduler-Java-Swing/assets/95756422/e415f461-3704-406d-8502-b7d1bbc29c04)

## Dependencies

- Java Swing

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/CPU-Scheduler-Java-Swing.git
Uploading README.md…]()
