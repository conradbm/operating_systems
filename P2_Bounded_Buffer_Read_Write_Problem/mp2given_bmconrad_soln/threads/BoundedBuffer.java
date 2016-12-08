/*
//class BoundedBuffer
//This class implements the synchronization methods to be used in 
//the bounded buffer problem 

public class BoundedBuffer
{
   //MP2 create any variables you need

	//Semaphor sema?
	//Lock mutex
	//int buffersize
	//int counter
	
   //BoundedBuffer
   //constructor:  initialize any variables that are needed for a bounded 
   //buffer of size "size"
   public BoundedBuffer(int size)
   {
   }

   //produce()
   //produces a character c.  If the buffer is full, wait for an empty
   //slot
   public void produce(char c)
   {
     //MP2
     //mutex.P();
	   //if counter == buffersize
	   	//{ 
	   	//    wait on consumer
	   // }
	   //else {
	   // 	  counter++ 
	   //     signal consumer
	   //	  }
	 //mutex.V();  
   }

   //consume()
   //consumes a character.  If the buffer is empty, wait for a producer.
   //use method SynchTest.addToOutputString(c) upon consuming a character. 
   //This is used to test your implementation.
   public void consume()
   {
     //MP2
	   //mutex.P();
	   //if(counter == 0)
	   //{
	   // wait for producer
	   //}
	   //else{
	   //counter--;
	   		//make sure you change the following line accordingly
	     SynchTest.addToOutputString('c');
	   //}
     // mutex.V();
   }

}
*/

import java.util.ArrayList;

//class BoundedBuffer
//This class implements the synchronization methods to be used in 
//the bounded buffer problem 

public class BoundedBuffer
{
   //MP2 create any variables you need

	int BUF_SIZE;    
    private int in;   // points to the next free position in the buffer
    private int out;  // points to the first filled position in the buffer
    private char[] buf;
    private Lock mutex; 
    private Semaphore empty;
    private Semaphore full;
    
   //BoundedBuffer
   //constructor:  initialize any variables that are needed for a bounded 
   //buffer of size "size"
   public BoundedBuffer(int size)
   {
	   in=0;
	   out=0;
	   BUF_SIZE = size;
	   mutex = new Lock("mutex");
	   empty = new Semaphore("empty",BUF_SIZE);
	   full = new Semaphore("full",0);
	   buf = new char[BUF_SIZE];
   }

   //produce()
   //produces a character c.  If the buffer is full, wait for an empty
   //slot
   public void produce(char c)
   {	       
	     //MP2
	   
	   	// Lock & secure.
	    empty.P();
		mutex.acquire();
			 
		// Critical section - i.e. shared variables.
	    buf[in] = c;
	    in = (in+1) % BUF_SIZE; 

	    // Release, leave critical section.
	    mutex.release();
	    full.V(); 
   }

   //consume()
   //consumes a character.  If the buffer is empty, wait for a producer.
   //use method SynchTest.addToOutputString(c) upon consuming a character. 
   //This is used to test your implementation.
   public void consume()
   {
	   //MP2
	   // Lock & secure.
	   full.P();
	   mutex.acquire();
	      	
	   // Critical Section, i.e,.. shared variables.
	   //make sure you change the following line accordingly
	   SynchTest.addToOutputString(buf[out]);
	   out = (out+1) % BUF_SIZE;
	   
	   // Release, leave critical section.
	   mutex.release();
	   empty.V();  
   }

}

