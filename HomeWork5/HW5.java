package HomeWork5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class HW5 {
    public static final int CARS_COUNT = 4;
    public static CountDownLatch beginDownLatch = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch endDownLatch = new CountDownLatch(CARS_COUNT);

        public static void main(String[] args) {
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
            Race race = new Race(new Road(60), new Tunnel(), new Road(40));
            Car[] cars = new Car[CARS_COUNT];
            for (int i = 0; i < cars.length; i++) {
                cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
            }

            ExecutorService executorService;
            executorService = Executors.newFixedThreadPool(CARS_COUNT);
            for (int i = 0; i < cars.length; i++) {
                executorService.submit(cars[i]);
            }
            try {
                beginDownLatch.await();
            } catch(InterruptedException exc) { }
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            try {
                endDownLatch.await();
            } catch(InterruptedException exc) { }
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
            executorService.shutdown();
        }
    public static class Car implements Runnable {
        private static int CARS_COUNT;
        public static AtomicInteger countReady = new AtomicInteger();
        public static AtomicBoolean haveWinner = new AtomicBoolean(false);
        private Race race;
        private int speed;
        private String name;
        public String getName() {
            return name;
        }
        public int getSpeed() {
            return speed;
        }
        public Car(Race race, int speed) {
            this.race = race;
            this.speed = speed;
            CARS_COUNT++;
            this.name = "Участник #" + CARS_COUNT;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.name + " готовится");
                Thread.sleep(500 + (int)(Math.random() * 800));
                System.out.println(this.name + " готов");
                beginDownLatch.countDown();
                countReady.getAndAdd(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (countReady.get() != CARS_COUNT) {
            }
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            if (haveWinner.get() == false) {
                haveWinner.set(true);
                System.out.println("We have winner: " + this.name);
            }
            endDownLatch.countDown();
        }

    }
    public static abstract class Stage {
        protected int length;
        protected String description;
        public String getDescription() {
            return description;
        }
        public abstract void go(Car c);
    }

    public static class Road extends Stage {
        public Road(int length) {
            this.length = length;
            this.description = "Дорога " + length + " метров";
        }
        @Override
        public void go(Car c) {
            try {
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
                System.out.println(c.getName() + " закончил этап: " + description);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Tunnel extends Stage {
        Semaphore semaphore = new Semaphore(CARS_COUNT/2, true);
        public Tunnel() {
            this.length = 80;
            this.description = "Тоннель " + length + " метров";
        }
        @Override
        public void go(Car c) {
            try {
                try {
                    System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                    semaphore.acquire();
                    System.out.println(c.getName() + " начал этап: " + description);
                    Thread.sleep(length / c.getSpeed() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(c.getName() + " закончил этап: " + description);
                    semaphore.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class Race {
        private ArrayList<Stage> stages;
        public ArrayList<Stage> getStages() { return stages; }
        public Race(Stage... stages) {
            this.stages = new ArrayList<>(Arrays.asList(stages));
        }
    }
}
