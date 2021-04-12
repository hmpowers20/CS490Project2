/*********************************************************
 CS 490 Semester Project - Phase 3
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 3): 04/19/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

public class HRRNProcessManager extends ProcessManager {
    /***********************************************************************
     Selects the next process according to the highest response ratio next algorithm
     @return the selected process
     ***********************************************************************/
    @Override
    public CPUProcess selectionHeuristic() {
        double maxRatio = 0;
        CPUProcess maxProcess = null;

        for (CPUProcess process : processes)
        {
            double waitingTime = ProcessScheduler.instance.currentTime - process.entryTime;
            double ratio = (waitingTime + process.getDuration()) / process.getDuration();
            if (ratio > maxRatio)
            {
                maxRatio = ratio;
                maxProcess = process;
            }
        }

        return maxProcess;
    }

    @Override
    public boolean reevaluateProcess() {
        return false;
    }
}