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

/***********************************************************************
 Class that manages queued processes and CPUs
 ***********************************************************************/
public class ProcessManager implements PropertyChangeListener {
    public static ProcessManager instance = new ProcessManager();

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
        cpus.get(0).setPaused(isPaused);
    }

    /***********************************************************************
     Adds a process
     ***********************************************************************/
    public void addProcess(CPUProcess process)
    {
        process.addPropertyChangeListener(this);
        processes.add(process);
        support.firePropertyChange("processes", null, processes);
    }

    /***********************************************************************
     Removes a process from the queue and returns it
     @return the popped process
     ***********************************************************************/
    public CPUProcess popProcess()
    {
        if (processes.size() > 0)
        {
            CPUProcess removedProcess = processes.remove();
            support.firePropertyChange("processes", null, processes);
            return removedProcess;
        }
        return null;
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
        for (CPU cpu : cpus)
        {
            if (cpu.getCurrentProcess().name == p.name)
                support.firePropertyChange("cpu1Process", null, p);

        }
    }
}
