package controllers;

import objects.interfaces.ActionElement;
import objects.interfaces.Element;
import objects.impls.Hero;
import objects.interfaces.Hittable;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HitController implements Runnable {
    private List<Element> elements;

    private List<Hittable> hittables;
    private List<ActionElement> actionElements;

    private Hero hero;

    private CyclicBarrier cyclicBarrier;

    private Thread thread;

    public HitController(List<Element> elements, List<Hittable> hittables,
                         Hero hero, List<ActionElement> actionElements,
                         CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
        this.elements = elements;
        this.hittables = hittables;
        this.hero = hero;
        this.actionElements = actionElements;
        init();
    }

    private void init(){
        this.elements.addAll(this.actionElements);
        this.thread = new Thread(this, "hit controller");
        this.thread.start();
    }

    private void checkHittings() {
        checkSides(hero);

        for (Hittable hittable : hittables) {
            if (hittable.isActive()) {
                checkSides(hittable);
            }
        }
        doActionOnHit();
    }

    private void doActionOnHit(){
        for (ActionElement a : actionElements) {
            if (hero.checkUp(a)) {
                a.downHit();
                hero.doAction(a.getAction());
            }
            if (hero.checkDown(a)) {
                a.uptHit();
                hero.doAction(a.getAction());
            }
            if (hero.checkRight(a)) {
                a.leftHit();
                hero.doAction(a.getAction());
            }

            if (hero.checkLeft(a)) {
                a.rightHit();
                hero.doAction(a.getAction());
            }
        }
    }

    private void checkSides(Hittable hittable) {
        boolean rightObs = false;
        boolean leftObs = false;
        boolean upObs = false;
        boolean downObs = false;

        if (hittable.isMoving()) {
            for (Element element : elements) {
                if (hittable != element) {
                    if (!rightObs) {
                        rightObs = hittable.checkRight(element);
                    }
                    if (!leftObs) {
                        leftObs = hittable.checkLeft(element);
                    }
                    if (!upObs) {
                        upObs = hittable.checkUp(element);
                    }
                    if (!downObs) {
                        downObs = hittable.checkDown(element);
                    }
                }
            }

            for (Hittable h : hittables) {
                if (hittable != h) {
                    if (!rightObs) {
                        rightObs = hittable.checkRight(h);
                    }
                    if (!leftObs) {
                        leftObs = hittable.checkLeft(h);
                    }
                    if (!upObs) {
                        upObs = hittable.checkUp(h);
                    }
                    if (!downObs) {
                        downObs = hittable.checkDown(h);
                    }
                }
            }

            hittable.setUp(upObs);
            hittable.setDown(downObs);
            hittable.setRight(rightObs);
            hittable.setLeft(leftObs);
        }
    }

    boolean checkUp(Hittable hittable) {
        boolean upObs = false;

        for (Element element : elements) {
            if (!upObs) {
                upObs = hittable.checkUp(element);
            }
            hittable.setUp(upObs);
        }
        return upObs;
    }

    boolean checkDown(Hittable hittable) {
        boolean downObs = false;

        for (Element element : elements) {
            if (!downObs) {
                downObs = hittable.checkDown(element);
            }
            hittable.setUp(downObs);
        }
        return downObs;
    }


    @Override
    public void run() {
        while (hero.isActive()) {
            checkHittings();
            try {
                makeDelay();
            } catch (InterruptedException | BrokenBarrierException e) {
                cyclicBarrier = null;
            }
        }
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            cyclicBarrier = null;
        }
    }

    private void makeDelay() throws BrokenBarrierException, InterruptedException {
        if (cyclicBarrier != null) {
            cyclicBarrier.await();
        }
    }

    public void addHittable(Hittable hittable) {
        hittables.add(hittable);
    }

    public void addAction(ActionElement actionElement) {
        actionElements.add(actionElement);
    }

    public void remove(Element element) {
        elements.remove(element);

        if (element instanceof Hittable) {
            hittables.remove(element);
        }

        if (element instanceof ActionElement) {
            actionElements.remove(element);
        }
    }

    public void stop() {
        thread.interrupt();
    }
}
