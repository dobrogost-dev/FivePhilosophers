import java.util.concurrent.Semaphore;

public class FivePhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;
    private static final int NUM_CHOPSTICKS = 5;
    private static final int MAX_EATING_PHILOSOPHERS = 4;

    private static Semaphore[] chopsticks;
    private static Semaphore eatingPhilosophers;

    public static void main(String[] args) {
        chopsticks = new Semaphore[NUM_CHOPSTICKS];
        eatingPhilosophers = new Semaphore(MAX_EATING_PHILOSOPHERS);

        for (int i = 0; i < NUM_CHOPSTICKS; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            int leftChopstickIndex = i;
            int rightChopstickIndex = (i + 1) % NUM_CHOPSTICKS;

            int finalI = i;
            Thread philosopher = new Thread(() -> {
                try {
                    while (true) {
                        // Wait for permission to eat
                        eatingPhilosophers.acquire();

                        // Acquire left fork
                        chopsticks[leftChopstickIndex].acquire();

                        // Acquire right fork
                        chopsticks[rightChopstickIndex].acquire();

                        // Eat
                        System.out.println("Philosopher " + finalI + " is eating...");
                        Thread.sleep(1000);

                        // Release left fork
                        chopsticks[leftChopstickIndex].release();

                        // Release right fork
                        chopsticks[rightChopstickIndex].release();

                        // Release permission to eat
                        eatingPhilosophers.release();

                        // Think
                        System.out.println("Philosopher " + finalI + " is thinking...");
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            philosopher.start();
        }
    }
}