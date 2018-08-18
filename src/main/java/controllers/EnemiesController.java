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
        for (int i = 0; i < movables.size(); i++) {
            Movable m = movables.get(i);
            if (m.isActive()) {
                m.moveDown();
                m.moveLeft();
                m.moveRight();
                m.moveUp();
            } else {
                movables.remove(m);
            }
        }
    }

    @Override
    public void run() {
        while (!stop) {
            refresh();
            try {
                if (cyclicBarrier != null) {
                    cyclicBarrier.await();
                } else {
                    return;
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                cyclicBarrier = null;
                System.out.println("enemies interrupted");
            }
        }
    }

    void spawnEnemy(Movable enemy) {
        movables.add(enemy);
    }

    public void stop() {
        thread.interrupt();
    }
}
