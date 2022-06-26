package threading;

/**
 * The following program illustrates the concept of deadlocks
 * and shows how it is induced in a two-resource scenario.
 * **/

public class InduceDeadlock {
    Object resource1 = new Object();
    Object resource2 = new Object();
    public static void main(String[] args) {
        InduceDeadlock induceDeadLock = new InduceDeadlock();
        Thread t1 = new Thread(() -> {
            synchronized(induceDeadLock.resource1) {
                System.out.println("Thread1 Locked resource1");
                synchronized (induceDeadLock.resource2) {
                    System.out.println("Thread1 Locked resource2");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (induceDeadLock.resource2) {
                System.out.println("Thread2 Locked resource2");
                synchronized (induceDeadLock.resource1) {
                    System.out.println("Thread2 Locked resource1");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
