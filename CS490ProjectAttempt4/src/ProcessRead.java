
public class ProcessRead {
        int arrival_time;
        String process_id;
        int service_time;
        int priority;


    public ProcessRead(int arrival_time,String process_id, double service_time, double priority) {
            this.arrival_time= arrival_time;
            this.process_id=process_id;
            this.service_time= (int) service_time;
            this.priority= (int) priority;
    }


        public int getArrival_time()
        {return arrival_time;}

        public String getProcess_id()
        {return process_id;}

        public int getService_time()
        {return service_time;}

        public int getPriority()
        {return priority;}

        public void setArrival_time(int arrival_time)
        {this.arrival_time = arrival_time;}

        public void setProcess_id(String process_id)
        {this.process_id = process_id;}

        public void setPriority(int priority)
        {this.priority = priority;}

        public void setService_time(int service_time)
        {this.service_time = service_time;}


    @Override
    public String toString() {
        return String.format("Arrival_Time: %d\r\nProcess_Id: %s\r\nService_time: %s\r\nPriority: %s\r\n",
                arrival_time, process_id, service_time, priority);
    }

}
