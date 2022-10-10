package threading;


import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class illustrates a thread race by simulating a real-life horse race.
 * We create {nHorses} and start them all, however, each waits for the barrier/gate (CyclicBarrier)
 * in the main to trip, so that they can start running
 * Note: The sleep times can be increased to make the result look interesting.
 */

public class HorseRace {
    public void horseRace(int nHorses) throws BrokenBarrierException, InterruptedException {
        AtomicInteger position = new AtomicInteger(0);
        CyclicBarrier barrier = new CyclicBarrier(nHorses + 1);
        List<Thread> horses = IntStream.range(1, nHorses + 1)
                .mapToObj(
                        i -> {
                            return new Thread(() -> {
                                try {
                                    barrier.await();
                                    System.out.println("Horse[%d] started!".formatted(i));
                                    // sleep induced to make all threads to start,
                                    // (before) so main gets a chance to invoke join!
                                    Thread.sleep(new Random().nextLong(1000));
                                    System.out.println("Horse[%d] finished at position %d"
                                            .formatted(i, position.incrementAndGet()));
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                } catch (BrokenBarrierException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                        }
                ).collect(Collectors.toList());
        for (Thread horse : horses) {
            horse.start();
        }
        // Open the gates.
        barrier.await();

        // Wait for all horses to finish!
        for (Thread horse : horses) {
            horse.join();
        }
        System.out.println("Race finished!");
    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        var inst = new HorseRace();
        inst.horseRace(10);
    }
}