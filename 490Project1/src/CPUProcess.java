/*********************************************************
 CS 490 Semester Project - Phase 3
 Contributors: Aaron Wells, Haley Powers, Taylor Buchanan
 Due Date (Phase 3): 04/19/2021
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

    public CPUProcess(CPUProcess process)
    {
        this.name = process.name;
        this.duration = process.duration;
        this.remainingDuration = process.remainingDuration;
        this.priority = process.priority;
        this.entryTime = process.entryTime;
        this.finishTime = process.finishTime;
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

    /***********************************************************************
     Getter for entry time
     @return The time the process enters
     ***********************************************************************/
    public double getEntryTime() {return entryTime;}

    /***********************************************************************
     Getter for the finished time
     @return The time the process is finished
     ***********************************************************************/
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

    /***********************************************************************
     Getter for the TAT value for processes
     @return The TAT value
     ***********************************************************************/
    public double getTAT() {
        return finishTime - entryTime;
    }

    /***********************************************************************
     Getter for the nTAT value for processes
     @return The nTAT value
     ***********************************************************************/
    public double getnTAT() {
        return getTAT() / duration;
    }
}