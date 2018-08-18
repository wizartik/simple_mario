package controllers;

import javafx.scene.Node;
import levels.Level;
import objects.interfaces.ActionElement;
import objects.interfaces.Element;
import objects.interfaces.Hittable;
import objects.interfaces.Movable;
import objects.impls.Hero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;

import static parameters.Settings.*;

public class LevelController {
    private Level level;

    private List<Element> elements;

    private HeroController heroController;

    private Set<Node> visibleNodes;
    private Node heroNode;
    private Set<Node> invisibleNodes;

    private double width;
    private double height;

    private double currentX;
    private double currentEndX;
    private double currentY;
    private double currentEndY;

    private double heroScreenX;
    private double heroScreenY;

    private HitController hitController;
    private EnemiesController enemiesController;
    private CyclicBarrier cyclicBarrier;

    public LevelController(Level level, double width, double height) {
        this.level = level;
        this.width = width;
        this.height = height;
        this.currentEndX = width;
        this.currentEndY = height;
        visibleNodes = new HashSet<>();
        invisibleNodes = new HashSet<>();

        level.setLevelController(this);
        cyclicBarrier = new CyclicBarrier(4);

        initControllers();
        initElements();
        fitNodes();

        heroNode = heroController.getNode();

    }

    public boolean isLoss(){
        return heroController.isLoss();
    }

    public boolean isVictory(){
        return heroController.isVictory();
    }

    public Set<Node> getVisibleNodes() {
        return visibleNodes;
    }

    private void initControllers() {
        Hero mario = new Hero(level.getHeroStartX(), level.getHeroStartY());

        List<Hittable> list = new CopyOnWriteArrayList<>(level.getHittables());
        List<Element> elements = new CopyOnWriteArrayList<>(level.getTextures());

        hitController = new HitController(elements, list, mario, level
                .getActionElements(), cyclicBarrier);
        this.heroController = new HeroController(mario, hitController, level
                .getLength(), cyclicBarrier);
        heroScreenX = heroController.getX();
        enemiesController = new EnemiesController(level.getEnemies(),cyclicBarrier);
    }

    private void initElements() {
        elements = new ArrayList<>(level.getTextures());
        elements.addAll(level.getHittables());
        elements.addAll(level.getEnemies());
        elements.addAll(level.getActionElements());
    }

    private void fitNodes() {

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (isFittingX(element) && isFittingY(element)) {
                visibleNodes.add(adjustElementPosition(element));
            } else {
                visibleNodes.remove(element.getNode());
            }
        }

    }

    private boolean isFittingX(Element element) {
        return (element.getX() <= currentEndX + LOAD_GAP)
                && (element.getX() + element.getWidth() + LOAD_GAP >= currentX);
    }

    private boolean isFittingY(Element element) {
        return ((element.getY() <= currentEndY + LOAD_GAP)
                && (element.getY() + element.getHeight() + LOAD_GAP >= currentY));
    }

    private Node adjustElementPosition(Element element) {
        double x = element.getX() - currentX;
        double y = element.getY() - currentY;
        element.getNode().relocate(x, y);
        return element.getNode();
    }

    public void refresh() {

        double heroX = heroController.getX();
        double heroEndX = heroX + heroController.getWidth();

        double heroY = heroController.getY();
        double heroEndY = heroY + heroController.getHeight();

        adjustScreenX(heroX, heroEndX);
        adjustScreenY(heroY, heroEndY);

        fitNodes();

        heroNode.relocate(heroScreenX, heroScreenY);
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void adjustScreenX(double heroX, double heroEndX) {
        if (heroEndX - currentX >= width * MOVE_SCREEN_X_COEF) {
            this.currentX = heroEndX - width * MOVE_SCREEN_X_COEF;
            this.currentEndX = currentX + width;
        } else if (heroX - currentX <= width * (1 - MOVE_SCREEN_X_COEF)) {
            this.currentX = heroX - width * (1 - MOVE_SCREEN_X_COEF);
            this.currentEndX = currentX + width;
        }
            this.heroScreenX = heroX - currentX;
    }


    private void adjustScreenY(double heroY, double heroEndY) {
        if (heroEndY - currentY >= height * MOVE_SCREEN_Y_COEF) {
            this.currentY = heroEndY - height * MOVE_SCREEN_Y_COEF;
            this.currentEndY = currentY + height;
        } else if (heroY - currentY <= height * (1 - MOVE_SCREEN_Y_COEF)) {
            this.currentY = heroY - height * (1 - MOVE_SCREEN_Y_COEF);
            this.currentEndY = currentX + height;
        }
        this.heroScreenY = heroY - currentY;
    }

    public void moveRight() {
        heroController.moveRight();
    }

    public void moveLeft() {
        heroController.moveLeft();
    }

    public void stopRunning() {
        heroController.stopRunning();
    }

    public void jump() {
        heroController.jump();
    }

    public Node getHeroNode() {
        return heroNode;
    }

    public void addNew(List<Node> old) {
        for (Node node :
                visibleNodes) {
            if (!old.contains(node)) {
                old.add(node);
            }
        }
    }

    public Set<Node> toRemove() {

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);

            if (!element.isVisible()) {
                visibleNodes.remove(element.getNode());
                invisibleNodes.add(element.getNode());
                elements.remove(element);
                hitController.remove(element);
                i--;
            }

            if (!element.isActive()) {
                hitController.remove(element);
            }

            if (!visibleNodes.contains(element.getNode())) {
                invisibleNodes.add(element.getNode());
            } else {
                invisibleNodes.remove(element.getNode());
            }
        }
        return invisibleNodes;
    }

    public void spawnEnemy(Element element) {
        elements.add(element);
        hitController.addHittable((Hittable) element);
        hitController.addAction((ActionElement) element);
        enemiesController.spawnEnemy((Movable) element);
    }

    public void doAction(Element element) {
        elements.add(element);
        enemiesController.spawnEnemy((Movable) element);
    }

    public void stop() {
        enemiesController.setStop(true);
        hitController.stop();
        enemiesController.stop();
        heroController.stop();
    }
}
