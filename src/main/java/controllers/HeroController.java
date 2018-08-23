package controllers;

import javafx.scene.Node;
import objects.impls.Hero;
import parameters.Settings;
import parameters.Sound;
import sound.SoundManager;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HeroController implements Runnable {

    private boolean moveRight;
    private boolean moveLeft;
    private boolean moving;
    private boolean jumping;

    private double[] jumpArray;
    private int counter;

    private Hero hero;

    private HitController hitController;

    private boolean victory;
    private boolean loss;
    private double victoryLength;

    private CyclicBarrier cyclicBarrier;

    private Thread thread;

    public HeroController(Hero hero, HitController hitController, double length,
                          CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
        this.hero = hero;
        this.hitController = hitController;
        this.victoryLength = length;
        init();
    }

    public double getWidth() {
        return hero.getWidth();
    }

    public double getHeight() {
        return hero.getHeight();
    }

    public Node getNode() {
        return hero.getNode();
    }

    public double getX() {
        return hero.getX();
    }

    public double getY() {
        return hero.getY();
    }

    public boolean isVictory() {
        return victory;
    }

    public boolean isLoss() {
        return loss;
    }

    private void init(){
        jumpArray = createJumpTrajectory();
        this.thread = new Thread(this, "hero controller");
        this.thread.start();
    }

    @Override
    public void run() {
        while (!isVictory() && !isLoss()) {
            processMovement();
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

    private void processMovement(){
        gravity();
        checkState();
        processMove();
        processJump();
    }

    public void moveRight() {
        moving = true;
        moveRight = true;
        moveLeft = false;
    }

    public void moveLeft() {
        moving = true;
        moveLeft = true;
        moveRight = false;
    }

    public void stopRunning() {
        moveLeft = false;
        moveRight = false;

        if (!jumping) {
            moving = false;
            hero.stop();
        }
    }

    public void jump() {
        if (!jumping) {
            SoundManager.playSound(Sound.JUMP);
            moving = true;
            jumping = true;
        }
    }

    private void stopJump() {
        jumping = false;
        counter = 0;
        if (!(moveLeft || moveRight)) {
            moving = false;
            hero.stop();
        }
        hero.stopJump();
    }

    private void processMove() {
        if (moveRight) {
            hero.moveRight();
        } else if (moveLeft) {
            hero.moveLeft();
        }
    }

    private void checkState() {
        victory = getX() > victoryLength;
        loss = getY() > 1300 || !hero.isActive();
    }

    private void processJump() {
        if (jumping) {
            if (jumpArray[counter] > 0) {
                moveUpBy(jumpArray[counter++]);
            } else {
                moveDownBy(Math.abs(jumpArray[counter++]));
            }
            if (counter >= jumpArray.length - 1) {
                stopJump();
            }
        }
    }

    private double[] createJumpTrajectory() {

        int points = (int) (Settings.JUMP_TIME / ((double) Settings.DELAY / 1000));
        double step = Math.PI / points;
        double[] trajectory = new double[points];

        double previous = 0;
        double argument = 0;
        for (int i = 0; i < trajectory.length; i++) {
            argument += step;
            double current = Math.sin(argument);
            trajectory[i] = (current - previous) * Settings.JUMP_HEIGHT;
            previous = current;
        }

        return trajectory;
    }

    private double g;
    private double lastY;

    private void gravity() {
        if (!jumping) {
            moveDownBy(Settings.HERO_VELOCITY + g);

            g += lastY != hero.getY() ? 1 : 0;
        }
        if (jumping || lastY == hero.getY()) {
            g = 0;
        }
        lastY = hero.getY();
    }

    private void moveUpBy(double value) {
        int counter = (int) (value / Settings.HERO_VELOCITY);

        for (int i = 0; i < counter; i++) {
            if (hitController.checkUp(hero)) {
                return;
            }
            hero.moveUp(Settings.HERO_VELOCITY);
        }
        if (hitController.checkUp(hero)) {
            return;
        }
        if (counter != 0) {
            hero.moveUp(value % counter);
        } else {
            hero.moveUp(value);
        }
    }

    private void moveDownBy(double value) {
        int counter = (int) (value / Settings.HERO_VELOCITY);

        for (int i = 0; i < counter; i++) {
            if (hitController.checkDown(hero)) {
                return;
            }
            hero.moveDown(Settings.HERO_VELOCITY);
        }
        if (hitController.checkDown(hero)) {
            return;
        }
        if (counter != 0) {
            hero.moveDown(value % counter);
        } else {
            hero.moveDown(value);
        }
    }

    public void stop() {
        thread.interrupt();
    }
}
