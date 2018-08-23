package controllers;

import objects.interfaces.Movable;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class EnemiesController implements Runnable {
    private List<Movable> movables;
    private volatile boolean stop = false;
    private Thread thread;

    private CyclicBarrier cyclicBarrier;

    EnemiesController(List<Movable> movables, CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
        this.movables = movables;
        this.thread = new Thread(this, "enemies controller");
        this.thread.start();
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }


    private void refresh() {
        movables.forEach(m -> {
            if (m.isActive()) {
                move(m);
            } else {
                movables.remove(m);
            }
        });
    }

    private void move(Movable m){
        m.moveDown();
        m.moveLeft();
        m.moveRight();
        m.moveUp();
    }

    @Override
    public void run() {
        while (!stop) {
            refresh();
            try {
                makeDelay();
            } catch (InterruptedException | BrokenBarrierException e) {
                cyclicBarrier = null;
            }
        }
    }

    private void makeDelay() throws BrokenBarrierException, InterruptedException {
        if (cyclicBarrier != null) {
            cyclicBarrier.await();
        }
    }

    void spawnEnemy(Movable enemy) {
        movables.add(enemy);
    }

    public void stop() {
        thread.interrupt();
    }
}
