import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;
import java.util.*;

public class ProcessManager implements PropertyChangeListener {
    public static ProcessManager instance = new ProcessManager();

    private Queue<CPUProcess> processes = new LinkedList<>();
    private List<CPU> cpus = new ArrayList<>();

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ProcessManager()
    {
        cpus.add(new CPU());
        cpus.get(0).start();
    }

    public Queue<CPUProcess> getProcesses()
    {
        return processes;
    }

    public void setCpuPause(boolean isPaused)
    {
        cpus.get(0).setPaused(isPaused);
    }

    public void addProcess(CPUProcess process)
    {
        process.addPropertyChangeListener(this);
        processes.add(process);
        support.firePropertyChange("processes", null, processes);
    }

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

    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        support.addPropertyChangeListener(listener);
    }

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
