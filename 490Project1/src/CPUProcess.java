import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.util.Timer;

public class CPUProcess {
    public String name;
    private double duration;
    public int priority;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public CPUProcess(String name, int duration, int priority)
    {
        this.name = name;
        this.duration = duration;
        this.priority = priority;
    }

    public double getDuration()
    {
        return duration;
    }

    public void setDuration(double duration)
    {
        support.firePropertyChange("duration", null, this);
        this.duration = duration;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        support.removePropertyChangeListener(listener);
    }
}
