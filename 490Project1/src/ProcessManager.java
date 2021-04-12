/*********************************************************
 CS 490 Semester Project - Phase 3
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 3): 04/19/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/***********************************************************************
 Class that manages queued processes and CPUs
 ***********************************************************************/
public abstract class ProcessManager {
    protected Queue<CPUProcess> processes = new LinkedList<>();
    private List<CPUProcess> finishedProcesses = new ArrayList<>();

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /***********************************************************************
     Getter for processes
     @return The queue of processes
     ***********************************************************************/
    public Queue<CPUProcess> getProcesses()
    {
        return processes;
    }

    /***********************************************************************
     Adds a process
     ***********************************************************************/
    public void addProcess(CPUProcess process)
    {
        processes.add(process);
        support.firePropertyChange("processes", null, processes);
    }

    /***********************************************************************
     Adds a finished process to the list of finished processes
     ***********************************************************************/
    public void addFinishedProcess(CPUProcess process)
    {
        finishedProcesses.add(process);
        support.firePropertyChange("finishedProcesses", null, finishedProcesses);
    }

    /***********************************************************************
     The getter for finished processes.
     @return the finished processes
     ***********************************************************************/
    public List<CPUProcess> getFinishedProcesses()
    {
        return finishedProcesses;
    }

    /***********************************************************************
     Adds a listener to the PropertyChangeListener
     ***********************************************************************/
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    /***********************************************************************
     Removes a process from the queue and returns it
     @return the popped process
     ***********************************************************************/
    public CPUProcess popProcess()
    {
        CPUProcess removedProcess = null;

        if (processes.size() > 0)
        {
            removedProcess = selectionHeuristic();
            processes.remove(removedProcess);
        }

        if (removedProcess != null)
            support.firePropertyChange("processes", null, processes);

        return removedProcess;
    }

    /***********************************************************************
     Selects the next process according to an algorithm
     @return the selected process
     ***********************************************************************/
    public abstract CPUProcess selectionHeuristic();

    /***********************************************************************
     Determine if current process should be reevaluated
     @return true if we should reevaluate current process
     ***********************************************************************/
    public abstract boolean reevaluateProcess();
}
