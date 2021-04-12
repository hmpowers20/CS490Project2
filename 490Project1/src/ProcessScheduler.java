/*********************************************************
 CS 490 Semester Project - Phase 3
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 3): 04/19/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class ProcessScheduler implements Runnable, PropertyChangeListener {
    public static ProcessScheduler instance = new ProcessScheduler();
    private Thread thread;
    public boolean isPaused = true;

    private Map<CPUProcess, Double> arrivingProcesses = new Hashtable<>();
    private List<CPU> cpus = new ArrayList<>();

    public double currentTime = 0;
    public static int timeUnit = 100;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /***************************
     Runs the start method.
     ***************************/
    public ProcessScheduler()
    {
        cpus.add(new CPU(new HRRNProcessManager()));
        cpus.get(0).pm.addPropertyChangeListener(this);
        cpus.get(0).start();

        cpus.add(new CPU(new RRProcessManager()));
        cpus.get(1).pm.addPropertyChangeListener(this);
        cpus.get(1).start();

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
                        for (CPU cpu : cpus)
                        {
                            CPUProcess process = new CPUProcess(entry.getKey());
                            process.addPropertyChangeListener(this);
                            cpu.pm.addProcess(process);
                        }
                        processesToRemove.add(entry.getKey());
                    }
                }
                for (CPUProcess process : processesToRemove) {
                    arrivingProcesses.remove(process);
                }

                currentTime += 50f / timeUnit;
                for (int i = 0; i < cpus.size(); i++)
                {
                    double nTATSum = 0;
                    for (CPUProcess process : cpus.get(i).pm.getFinishedProcesses())
                    {
                        nTATSum += process.getnTAT();
                    }
                    support.firePropertyChange("averageNTAT" + i, null, nTATSum / cpus.get(i).pm.getFinishedProcesses().size());
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

    /***********************************************************************
     Sets pause state on CPUs
     ***********************************************************************/
    public void setCpuPause(boolean isPaused)
    {
        ProcessScheduler.instance.isPaused = isPaused;
        for (CPU cpu : cpus)
        {
            cpu.setPaused(isPaused);
        }
    }

    /***********************************************************************
     Contains the logic to send updates from CPUs to the GUI
     ***********************************************************************/
    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        if (event.getSource() instanceof CPUProcess) {
            CPUProcess p = (CPUProcess)event.getNewValue();

            for (int i = 0; i < cpus.size(); i++) {
                CPUProcess cpuProcess = cpus.get(i).getCurrentProcess();
                if (cpuProcess != null && cpuProcess.name == p.name) {
                    support.firePropertyChange("cpu" + (i + 1) + "Process", null, p);
                }
            }
        }
        else if (event.getSource() instanceof ProcessManager) {
            for (int i = 0; i < cpus.size(); i++) {
                if (event.getSource() == cpus.get(i).pm) {
                    switch (event.getPropertyName()) {
                        case ("processes"):
                            support.firePropertyChange("processes" + i, null, event.getNewValue());
                            break;
                        case ("finishedProcesses"):
                            support.firePropertyChange("finishedProcesses" + i, null, event.getNewValue());
                            break;
                    }
                }
            }
        }
    }
}
