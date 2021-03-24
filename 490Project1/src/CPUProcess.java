/*********************************************************
 CS 490 Semester Project - Phases 1 & 2
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 2): 03/26/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/***********************************************************************
 Class that represents a process
 ***********************************************************************/
public class CPUProcess {
    public String name;
    private double duration;
    public int priority;

    public double entryTime;
    public double finishTime;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /***********************************************************************
     Constructor that sets initial values
     ***********************************************************************/
    public CPUProcess(String name, int duration, int priority, int entryTime)
    {
        this.name = name;
        this.duration = duration;
        this.priority = priority;
        this.entryTime = entryTime;
    }

    /***********************************************************************
     Getter for duration
     @return The duration
     ***********************************************************************/
    public double getDuration()
    {
        return duration;
    }

    /***********************************************************************
     Setter for duration
     ***********************************************************************/
    public void setDuration(double duration)
    {
        this.duration = duration;
        support.firePropertyChange("duration", null, this);
    }

    /***********************************************************************
     Adds a listener to the PropertyChangeSupport
     ***********************************************************************/
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    /***********************************************************************
     Removes a listener to the PropertyChangeSupport
     ***********************************************************************/
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }
}
