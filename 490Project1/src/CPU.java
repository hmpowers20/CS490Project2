/*********************************************************
 CS 490 Semester Project - Phase 3
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 3): 04/19/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

/***********************************************************************
 This class represents one of the CPUs which runs processes
 ***********************************************************************/
public class CPU implements Runnable
{
    private Thread thread;
    private CPUProcess currentProcess;
    private boolean isPaused = true;
    public ProcessManager pm;

    /***********************************************************************
     Setter for isPaused
     ***********************************************************************/
    public CPU(ProcessManager pm)
    {
        this.pm = pm;
    }

    /***********************************************************************
     Setter for isPaused
     ***********************************************************************/
    public void setPaused(boolean isPaused)
    {
        this.isPaused = isPaused;
    }

    /***********************************************************************
     Getter for currentProcess
     @return The current process
     ***********************************************************************/
    public CPUProcess getCurrentProcess() {
        return currentProcess;
    }

    /***********************************************************************
     Implementation of Runnable.run. Gets a process to run and simulates running it.
     ***********************************************************************/
    @Override
    public void run()
    {
        while(true) {
            if (!isPaused) {
                currentProcess = pm.popProcess();
            }

            if (currentProcess != null) {
                while (currentProcess.getRemainingDuration() > 0) {
                    if (!isPaused) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        currentProcess.setRemainingDuration(currentProcess.getRemainingDuration() - (50f / ProcessScheduler.timeUnit));
                        if (currentProcess.getRemainingDuration() <= 0) {
                            currentProcess.finishTime = ProcessScheduler.instance.currentTime;
                            pm.addFinishedProcess(currentProcess);
                            currentProcess = pm.popProcess();
                            if (currentProcess == null)
                                break;
                        }

                        if (pm.reevaluateProcess())
                        {
                            pm.addProcess(currentProcess);
                            currentProcess = pm.popProcess();
                            if (currentProcess == null)
                                break;
                        }
                    }
                    else
                    {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /***********************************************************************
     Starts the CPU
     ***********************************************************************/
    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }
}