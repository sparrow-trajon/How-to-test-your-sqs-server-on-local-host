package multithreadreceiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*
* 
*
* @author Manish
*
*/
public class MultiThreadReceiver {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();

		ExecutorService executor = Executors.newFixedThreadPool(3);
		for (int i = 1; i <= 3; i++) {
			Runnable worker = new Worker("" + i);
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");

		long end = System.currentTimeMillis();
		System.out.println((end - start) + "ms");
	}
}
