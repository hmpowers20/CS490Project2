import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class parseF implements Runnable{
    private volatile boolean isStopped = false;

    public static void File () throws FileNotFoundException, InterruptedException {

        //FileReader input = new FileReader(new File("test123.txt"));
        int A_T;
        String id;
        double S_T;
        double P;
        Scanner input = new Scanner(new File("test12.txt"));
        input.useDelimiter(",|\n");

        //-------------------------------------------------------------------------
        //This parser will create both an array list and a queue both
        //processes can be used in tandem to either peek or creat FIFO
        // item implementation.
        //--------------------------------------------------------------------------
        Window mywin = new Window();


        ArrayList<ProcessRead> processes = new ArrayList<ProcessRead>();
        Queue<ArrayList<ProcessRead>> Process_Collect = null;
        CPU_Pause wait = new CPU_Pause();
        while (input.hasNext()) {
            {
                A_T = input.nextInt();
                id = input.next();
                S_T = Double.parseDouble(input.next().substring(1));
                P = Double.parseDouble(input.next().substring(1));

                processes.add(new ProcessRead(A_T, id, S_T, P));
                Process_Collect = new LinkedList<>();
                Process_Collect.add(processes);
                CPU_Pause.Thread_Run();
            }

            CPU_Pause.Thread_Run();

            // System.out.println(processes);
            //used for queue printing.
            // for(ArrayList<ProcessRead> s : Process_Collect) { System.out.println("im inside of your QUEUE" + s.toString());
            // }
            //used for array list printing
        }

        for (int i = 0; i < processes.size(); i++) {
            // System.out.print("im inside of your array list" + processes.get(i));
            System.out.println( processes.get(0).getArrival_time());
            System.out.println( processes.get(1).getArrival_time());
            System.out.println( processes.get(2).getArrival_time());

        } }
    //peek function peeks at first element in the Queue
    //  System.out.println(Process_Collect.peek());
    @Override
    public void run() {
        while(!isStopped){ System.out.println("thread is running.....");
            System.out.println("thread is now going to pause");
            try { Thread.sleep(200); } catch (InterruptedException e) { e.printStackTrace();
            } System.out.println("thread is now resumed ..");
        } System.out.println("thread is stopped....");
    } public void stop(){ isStopped = true;}}







