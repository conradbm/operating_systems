//class Database
//This class implements the synchronization methods to be used in 
//the readers writers problem

public class Database
{
   //MP2 create any variables that you need for implementation of the methods
   //of this class

	
	/* Solution Explaination --
	 * NOTE: The output has a similar pattern as the standard solution!
	 * Ex) Some writers, some readers, ALOT of writers, then ALOT of readers.
	 * 
	 * In my solution I have could not mimic the output from the std.out file exactly, however, I believe it does
	 *  display a writer priority driven solution. I accomplish this by:
	 *  1) forcing readers to a higher restriction, the try_to_read_mutex, which locks up for any # of writers > 0,
	 *  	the writers do not have this restriction, making them more free to pivot in and out of the thread pool.
	 *  2) Contrariwise, the readers lock up the resource mutex, which is needed in SynchTest.java before the critical 
	 *  	section. So the writers are a bit restricted, in that they cannot write if a reader has made it as far as 
	 *  	they have. This allows for mutual exclusion, but a priority given to the writers. 
	 */
	
	int readcount;
	int writecount;
	int active_readers,waiting_readers;
	int active_writers,waiting_writers;
	
	Semaphore mutex;
	Semaphore rwmutex;
	Semaphore try_to_read_mutex;
	Semaphore resource;
	Semaphore read_mutex;
	Semaphore write_mutex;
	Semaphore block_reader;
	Semaphore block_writer;
	Condition ok_to_read;
	Condition ok_to_write;
	Lock mutex_lock;
	
   //Database
   //Initializes Database variables
   public Database()
   {
	   readcount=writecount=active_readers=active_writers=waiting_readers=waiting_writers=0;

     //MP2

	   mutex = new Semaphore("mutex",1);
	   rwmutex = new Semaphore("rwmutex",1);
	   read_mutex = new Semaphore("read_mutex",1);
	   write_mutex = new Semaphore("write_mutex",1);
	   try_to_read_mutex = new Semaphore("try_to_read_mutex",1);
	   ok_to_read = new Condition("ok_to_read");
	   ok_to_write = new Condition("ok_to_write");
	   resource = new Semaphore("resource",1);
	   mutex_lock = new Lock("mutex");
	   block_reader = new Semaphore("block_reader",1);
	   block_writer = new Semaphore("block_writer",1);
   }

   //napping()
   //this is called when a reader or writer wants to go to sleep and when 
   //a reader or writer is doing its work.
   //Do not change for MP2
   public static void napping()
   {
      Alarm ac = new Alarm(20);  
      
   }

   //startRead
   //this function should block any reader that wants to read if there 
   //is a writer that is currently writing.
   //it returns the number of readers currently reading including the
   //new reader.
   public int startRead() throws InterruptedException
   {
	   
	   // MP2 FOR WRITER PRIORITY
	   try_to_read_mutex.P();
	    read_mutex.P();
	      active_readers++;
	      if (active_readers == 1)
	          resource.P();
	    read_mutex.V();
	  try_to_read_mutex.V();
	  
	  
      //MP2 FOR READER PRIORITY 
	  /*
	   mutex.P();
	   readcount++;
	   if(readcount==1)
	   {
		   block_writer.P();
	   }	   
	   mutex.V();
		*/
	   
	   return active_readers;
   }

   //endRead()
   //This function is called by a reader that has finished reading from the 
   //database.  It returns the current number of readers excluding the one who
   //just finished.
   public int endRead()
   {   
	   
	   // MP2 FOR WRITER PRIORITY
	   read_mutex.P();
	   active_readers--;
	   if (active_readers == 0)
		   resource.V();
	   read_mutex.V();
	   
      //MP2 FOR READER PRIORITY
	   /*
		  mutex.P();
		  readcount--;
		  if(readcount==0)
		  {
			  rwmutex.V();
		  }
		  mutex.V();
	    */
	  
	   return active_readers;
   }

   //startWrite()
   //This function should allow only one writer at a time into the Database
   //and block the writer if anyone else is accessing the database for read 
   //or write.
   public void startWrite()
   {   

	   // MP2 FOR WRITER PRIORITY
	   //resource.P();
	   write_mutex.P();
	   active_writers++;
	  
	  // Writers are waiting and active, lock up the readers.
	   if (active_writers == 1)
		   try_to_read_mutex.P();
	   write_mutex.V();
	  
      //MP2 FOR READER PRIORITY
	   //block_writer.P();
   		
   }
   
   //endWrite()
   //signal that a writer is done writing and the database can now be accessed
   //by someone who is waiting to read or write.
   public void endWrite()
   {

	   // MP2 FOR WRITER PRIORITY
	   	write_mutex.P();
	   	active_writers--;
	   
	   	// No more active or waiting writers, unlock readers.
	   	if (active_writers == 0)
	   		try_to_read_mutex.V();
	   	write_mutex.V();
	   	//resource.V();
	   
	   	//MP2 FOR READER PRIORITY 	   
	   	//rwmutex.V();
   }
}

