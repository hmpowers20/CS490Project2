public class CPU implements Runnable
{
    private Thread thread;
    private CPUProcess currentProcess;
    private boolean isPaused = true;

    public void setPaused(boolean isPaused)
    {
        this.isPaused = isPaused;
    }

    public void run()
    {
        while(true) {
            if (!isPaused) {
                currentProcess = ProcessManager.instance.popProcess();
            }

            if (currentProcess != null) {
                while (currentProcess.getDuration() > 0) {
                    if (!isPaused) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        currentProcess.setDuration(currentProcess.getDuration() - .05);
                        if (currentProcess.getDuration() <= 0) {
                            currentProcess = ProcessManager.instance.popProcess();
                        }
                    }
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start()
    {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }
}