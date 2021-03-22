/*********************************************************
 CS 490 Semester Project - Phases 1 & 2
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 2): 03/26/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***********************************************************************
 Class that manages queued processes and CPUs
 ***********************************************************************/
public class ProcessManager implements PropertyChangeListener {
    public static ProcessManager instance = new ProcessManager();

    public Lock queueLock = new ReentrantLock();

    private Queue<CPUProcess> processes = new LinkedList<>();
    private List<CPU> cpus = new ArrayList<>();

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /***********************************************************************
     Constructor that starts the CPUs
     ***********************************************************************/
    public ProcessManager()
    {
        cpus.add(new CPU());
        cpus.get(0).start();

        cpus.add(new CPU());
        cpus.get(1).start();
    }

    /***********************************************************************
     Getter for processes
     @return The queue of processes
     ***********************************************************************/
    public Queue<CPUProcess> getProcesses()
    {
        return processes;
    }

    /***********************************************************************
     Sets pause state on CPUs
     ***********************************************************************/
    public void setCpuPause(boolean isPaused)
    {
        for (CPU cpu : cpus)
        {
            cpu.setPaused(isPaused);
        }
    }

    /***********************************************************************
     Adds a process
     ***********************************************************************/
    public void addProcess(CPUProcess process)
    {
        process.addPropertyChangeListener(this);
        queueLock.lock();
        try {
            processes.add(process);
        } finally {
            queueLock.unlock();
        }
        support.firePropertyChange("processes", null, processes);
    }

    /***********************************************************************
     Removes a process from the queue and returns it
     @return the popped process
     ***********************************************************************/
    public CPUProcess popProcess()
    {
        CPUProcess removedProcess = null;

        queueLock.lock();
        try {
            if (processes.size() > 0)
            {
                removedProcess = processes.remove();
            }
        } finally {
            queueLock.unlock();
        }

        if (removedProcess != null)
            support.firePropertyChange("processes", null, processes);

        return removedProcess;
    }

    /***********************************************************************
     Adds a listener to the PropertyChangeListener
     ***********************************************************************/
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    /***********************************************************************
     Contains the logic to send updates from CPUs to the GUI
     ***********************************************************************/
    @Override
    public void propertyChange(PropertyChangeEvent event)
    {
        CPUProcess p = (CPUProcess)event.getNewValue();
        for (int i = 0; i < cpus.size(); i++)
        {
            CPUProcess cpuProcess = cpus.get(i).getCurrentProcess();
            if (cpuProcess != null && cpuProcess.name == p.name) {
                support.firePropertyChange("cpu" + 1 + "Process", null, p);
            }
        }
    }
}
