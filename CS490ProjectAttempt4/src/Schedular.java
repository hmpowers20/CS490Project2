import java.util.ArrayList;

class Scheduler<CPUProcess> {
    //this class handles distribution of processes to cpus
    //not really needed with one cpu but will be useful later

    ArrayList<CPUProcess> ProcessQueue = new ArrayList<CPUProcess>();
    public Scheduler()
    {

    }
    public Scheduler(parseF parser)
    {
        //constructor with parser object
        try{
            parser.File();
            //get the processes here and dump them into arraylist.


        }
        catch (Exception e){
            System.out.println("File not found");

        }


    }


    void start_stop(Boolean status)
    {
        //function to start or stop execution

    }
    void init(String filename)
    {
        //initializes the things
        //
    }
    void addProcess(CPUProcess process)
    {
        //add a process to the queue
        ProcessQueue.add(process);
    }
    CPUProcess getNextProcess()
    {
        //returns the next process in the queue
        return ProcessQueue.get(ProcessQueue.size());

    }

}
