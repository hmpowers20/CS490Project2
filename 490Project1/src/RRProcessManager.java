/*********************************************************
 CS 490 Semester Project - Phase 3
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 3): 04/19/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

public class RRProcessManager extends ProcessManager {
    public static double timeQuantum = 10;
    public double lastQuantumTick = 0;

    /***********************************************************************
     Selects the next process according to the round robin algorithm
     @return the selected process
     ***********************************************************************/
    @Override
    public CPUProcess selectionHeuristic() {
        return processes.peek();
    }

    @Override
    public boolean reevaluateProcess() {
        if (ProcessScheduler.instance.currentTime - lastQuantumTick >= timeQuantum) {
            lastQuantumTick = ProcessScheduler.instance.currentTime;
            return true;
        }
        return false;
    }
}