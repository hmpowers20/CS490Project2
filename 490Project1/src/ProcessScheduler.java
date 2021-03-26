/*********************************************************
 CS 490 Semester Project - Phases 1 & 2
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 2): 03/26/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ProcessScheduler implements Runnable {
    public static ProcessScheduler instance = new ProcessScheduler();
    private Thread thread;
    public boolean isPaused = true;
    private boolean hasBeenStarted = false;

    private Map<CPUProcess, Double> arrivingProcesses = new Hashtable<>();

    public double currentTime = 0;
    public int timeUnit = 100;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /***************************
     Runs the start method.
     ***************************/
    public ProcessScheduler()
    {
        start();
    }

    /***********************************************************************
     Adds a process to the list of arriving processes.
     ***********************************************************************/
    public void addArrivingProcess(CPUProcess cpuProcess) {
        arrivingProcesses.put(cpuProcess, (double) cpuProcess.entryTime);
    }

    /***********************************************************************
     Adds a listener to the PropertyChangeListener
     ***********************************************************************/
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    /***********************************************************************************************
     Implementation of Runnable.run. Updates current time and determines if a process should arrive.
     ***********************************************************************************************/
    @Override
    public void run() {
        while(true) {
            if (!isPaused) {
                List<CPUProcess> processesToRemove = new ArrayList<>();
                for (Map.Entry<CPUProcess, Double> entry : arrivingProcesses.entrySet()) {
                    entry.setValue(entry.getValue() - (50f / timeUnit));
                    if (entry.getValue() <= 0) {
                        ProcessManager.instance.addProcess(entry.getKey());
                        processesToRemove.add(entry.getKey());
                    }
                }
                for (CPUProcess process : processesToRemove) {
                    arrivingProcesses.remove(process);
                }

                currentTime += 50f / timeUnit;
                support.firePropertyChange("time", null, new double[]{currentTime, ProcessManager.instance.getFinishedProcesses().size()});
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
