package gjt.usblab.API;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        // Create a CountDownLatch with a count of 2
        CountDownLatch latch = new CountDownLatch(2);

        // Create and start two threads
        Thread thread1 = new Thread(new Worker(latch, "thread1"));
        Thread thread2 = new Thread(new Worker(latch, "thread2"));
        thread1.start();
        thread2.start();

        // Main thread will wait until both worker threads complete their work
        latch.await();  // This will block until the count of latch is 0

        System.out.println("Both threads have finished their work.");
    }
}

class Worker implements Runnable {
    private CountDownLatch latch;
    private String inputString;

    public Worker(CountDownLatch latch, String s) {
        this.latch = latch;
        this.inputString = s;
    }

    @Override
    public void run() {
        try {
            // Simulate work
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + " has finished work.--" + inputString);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Decrease the count of latch by 1
            latch.countDown();
        }
    }
}