import java.util.*;

public class ProcessScheduler implements Runnable {
    public static ProcessScheduler instance = new ProcessScheduler();
    private Thread thread;
    public boolean isPaused = true;

    private Map<CPUProcess, Double> arrivingProcesses = new Hashtable<>();

    public double currentTime = 0;
    public int timeUnit = 100;

    public ProcessScheduler()
    {
        start();
    }

    public void addArrivingProcess(CPUProcess cpuProcess) {
        arrivingProcesses.put(cpuProcess, (double) cpuProcess.entryTime);
    }

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
            }

            try {
                Thread.sleep(50);
                currentTime += 50;
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
