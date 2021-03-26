/*********************************************************
 CS 490 Semester Project - Phases 1 & 2
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 2): 03/26/2021
 CS 490-02 -- Professor Allen
 *********************************************************/

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;

/***********************************************************************
 Class that represents a process
 ***********************************************************************/
public class CPUProcess {
    public String name;
    private double duration;
    private double remainingDuration;
    public int priority;

    public double entryTime;
    public double finishTime;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private static DecimalFormat df = new DecimalFormat("0.00");
    /***********************************************************************
     Constructor that sets initial values
     ***********************************************************************/
    public CPUProcess(String name, int duration, int priority, int entryTime)
    {
        this.name = name;
        this.duration = duration;
        this.remainingDuration = duration;
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
     Getter for remaining duration
     @return The remaining duration
     ***********************************************************************/
    public double getRemainingDuration()
    {
        return remainingDuration;
    }

    public double getEntryTime() {return entryTime;}

    public double getFinishTime() {return finishTime;}

    /***********************************************************************
     Setter for duration
     ***********************************************************************/
    public void setRemainingDuration(double remainingDuration)
    {
        this.remainingDuration = remainingDuration;
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

    public double getTAT() {
        return finishTime - entryTime;
    }

    public double getnTAT() {
        return getTAT() / duration;
    }
}