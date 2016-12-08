
class SchedulerPair 
{
   public int prior, burst;

   public SchedulerPair(int p, int b)
   {
      prior = p;
      burst = b;
   }
}


class SimpleRunnable implements Runnable {
   private int burst;

   public SimpleRunnable(int b)
   {
      burst = b;
   }

   public void run() {
	   // You may only want to do this for so many seconds, then pause to go to the next
      SchedulerTest.SimulateThreadWork(burst);
   }
}

public class SchedulerTest implements Runnable
{
   List procList;
   static char letter = 'A';
   static int type;

public SchedulerTest(List procList)
{
   this.procList = procList;
}

public static void initializeSchedulerType(String name)
{
  if (name == null || name.equalsIgnoreCase(new String("fcfs"))) // from the name, deduce the test to run 
  {
       Scheduler.setSchedulerPolicy(Scheduler.POLICY_FCFS);
       System.out.println("Testing First Come First Serve Scheduling");
  }
  else if (name.equalsIgnoreCase(new String("rr"))) 
  {
       Scheduler.setSchedulerPolicy(Scheduler.POLICY_RR);
       System.out.println("Testing Round Robin Scheduling");
  }
  else if (name.equalsIgnoreCase(new String("sjf_np"))) 
  {
       Scheduler.setSchedulerPolicy(Scheduler.POLICY_SJF_NP);
       System.out.println("Testing Shortest Job First Non-preemptive Scheduling");
  }
  else if (name.equalsIgnoreCase(new String("sjf_p"))) 
  {
       Scheduler.setSchedulerPolicy(Scheduler.POLICY_SJF_P);
       System.out.println("Testing Shortest Job First Preemptive Scheduling");
  }
  else if (name.equalsIgnoreCase(new String("prio_np"))) 
  {
       Scheduler.setSchedulerPolicy(Scheduler.POLICY_PRIO_NP);
       System.out.println("Testing Priority Non-preemptive Scheduling");
  }
  else if (name.equalsIgnoreCase(new String("prio_p"))) 
  {
       Scheduler.setSchedulerPolicy(Scheduler.POLICY_PRIO_P);
       System.out.println("Testing Priority Preemptive Scheduling");
  }
}




//----------------------------------------------------------------------
// SimulateThreadWork
//      Each thread has a loop whose size reflects the burst time of the
//      job
//----------------------------------------------------------------------

public static void SimulateThreadWork(int time)
{
    NachosThread.thisThread().print();
    System.out.print("Starting Burst of "+ time + " ");
    Nachos.stats.printElapsedTicks();
    while ( time-- != 0 ) {  //decrement's after check. Notice, there is an interrupt check each iteration.
        Interrupt.oneTick(); 
        NachosThread.thisThread().print();
        NachosThread.thisThread().setTimeLeft(time);
        System.out.print("Still " + time + " to go ");
        Nachos.stats.printElapsedTicks();
    }
    NachosThread.thisThread().print();
    System.out.print("Done with burst ");
    Nachos.stats.printElapsedTicks();
    
}

//-------------------------------------------------------------------
// run 
//
//     Schedules each thread when it is time for it to arrive.
//
//------------------------------------------------------------------

public void run()
{

	    String str = new String("thread");
	    String name;
	    Integer startTime;
	 
	    // fork any threads that are ready.
	    startTime = procList.sortedPeek(); 
	    //System.out.println("total ticks: " + Nachos.stats.totalTicks);
	    
	    while( (startTime != null) && (Nachos.stats.totalTicks >= startTime.intValue() ) )
	    {
	       // Schedule it.
	    	
	    	// pop off the list
	       SchedulerPair alpha = (SchedulerPair)procList.sortedRemove().getItem();
	       
	       // create a runnable object for our new thread to use 
	       SimpleRunnable r = new SimpleRunnable(alpha.burst); 
	       name = new String(str + letter);
	       NachosThread newOne = new NachosThread(name, alpha.prior, alpha.burst);
	       System.out.println("Queuing thread "+name+ " at Time " + Nachos.stats.totalTicks + ", priority "+ alpha.prior);
	       newOne.fork(r); 
	       // more work later so schedule yourself again.
	       letter++;
	       
	       startTime = procList.sortedPeek();

	       if( (startTime != null) && ( Nachos.stats.totalTicks < startTime.intValue()) )
	       {
	    	   Interrupt.schedule( this, startTime.intValue() - Nachos.stats.totalTicks, Interrupt.TimerInt );
	       }
	    }

	

}

public static void ThreadTest(int type2)
{
    int numThreads = 11;
    int startTime[];
    int burstTime[];
    int priority[];
    type = type2;
    if (type == Scheduler.POLICY_FCFS)
    {
       int st[] = {   0,   0,   0, 100, 100, 100, 500, 500, 500,   500,   500};
       int bt[] = {   7,   2,   5,  12,  15,   12,   2,   3,   8,   4,   8};
       int p[] = {   0,   0,   0,  0,  0,   0,   0,   0,   0,   0,   0};
       startTime = st;
       burstTime = bt;
       priority = p;
    }
    else if (type == Scheduler.POLICY_RR)
    {
       int st[] = {   0,   0,   0, 100, 100, 100, 200, 250, 250,   250,   250};
       int bt[] = {   7,   9,   9,  12,  15,   12,   14,   13,   9,   14,   13};
       int p[] = {   0,   0,   0,  0,  0,   0,   0,   0,   0,   0,   0};
       startTime = st;
       burstTime = bt;
       priority = p;
    }
    else if (type == Scheduler.POLICY_SJF_NP)
    {
       int st[] = {   0,   0,   0, 100, 100, 100, 200, 250, 250,   250,   250};
       int bt[] = {   7,   22,   2,  6,  15,   3,   14,   18,   9,   12,   3};
       int p[] = {   0,   0,   0,  0,  0,   0,   0,   0,   0,   0,   0};
       startTime = st;
       burstTime = bt;
       priority = p;
    }
    else if (type == Scheduler.POLICY_SJF_P)
    {
       int st[] = {   0,   0,   0, 100, 100, 100, 200, 250, 250,   250,   250};
       int bt[] = {   7,   22,   2,  6,  15,   3,   14,   18,   9,   12,   3};
       int p[] = {   0,   0,   0,  0,  0,   0,   0,   0,   0,   0,   0};
       startTime = st;
       burstTime = bt;
       priority = p;
    }
    else if (type == Scheduler.POLICY_PRIO_NP)
    {
       int st[] = {   0,   0,   0, 100, 100, 100, 200, 250, 250,   250,   250};
       int bt[] = {   7,   9,   2,  6,  15,   3,   14,   18,   9,   12,   3};
       int p[] = {   1,   2,   0,  0,  1,   2,   1,   2,   2,   0,   1};
       startTime = st;
       burstTime = bt;
       priority = p;
    }
    else if (type == Scheduler.POLICY_PRIO_P)
    {
       int st[] = {   0,   0,   0, 100, 100, 100, 200, 250, 250,   250,   250};
       int bt[] = {   7,   9,   2,  6,  15,   3,   14,   18,   9,   12,   3};
       int p[] = {   1,   2,   0,  0,  1,   2,   1,   2,   2,   0,   1};
       startTime = st;
       burstTime = bt;
       priority = p;
    }
    else
    {
       System.out.println("Invalid scheduling algorithm");
       return;
    }
    
    // SchedulerPair is a struct w/ burstTime and Priority.
    SchedulerPair a_pair;
    List procList = new List();

    for( int i = 0; i < numThreads; i++ ) {
       a_pair = new SchedulerPair(priority[i], burstTime[i]);
       procList.sortedInsert(a_pair, startTime[i]);
    }
    
    SchedulerTest st = new SchedulerTest(procList);

    System.out.print("Starting at ");
    Nachos.stats.printElapsedTicks();
    System.out.println("Queuing threads.");

    st.run();
}

}


