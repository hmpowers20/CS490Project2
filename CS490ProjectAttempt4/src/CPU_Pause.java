import static java.lang.Thread.currentThread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

//---------------------------------------------------------------------
//This implementation is incomplete but runs correctly, this will allow
// processes to be waited on and restarted, it may need to be connected
//to another class other than the parser class but can be changed
//----------------------------------------------------------------------


public class CPU_Pause {


    public static  void Thread_Run() throws InterruptedException {
       parseF mine = new parseF();

        Thread cpu = new Thread((Runnable) mine);

        cpu.start();
        System.out.println(currentThread().getName() + " is stopping thread");
        cpu.stop();

        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println(currentThread().getName() + " is stopping  thread");

        }}
