package objects.impls;

import controllers.LevelController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.interfaces.ActionElement;
import objects.interfaces.Hittable;
import objects.interfaces.Movable;
import parameters.Action;
import parameters.Sound;
import sound.SoundManager;

public class Enemy implements Movable, Hittable, ActionElement {

    private double x;
    private double y;

    private double velocity = 5;

    private boolean active;
    private boolean visible;

    private boolean moveRight;

    private Node node;
    private ImageView image;

    private boolean moving = true;
    private boolean forbidDown = true;

    private Action action = Action.DIE;

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;

        active = true;
        visible = true;
        image = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/enemy.png")
                .toExternalForm()));

        moveRight = true;
        node = image;
    }

    @Override
    public synchronized void setRight(boolean permit) {
        if (moveRight && permit) {
            moveRight = false;
        }
    }

    @Override
    public synchronized void setLeft(boolean permit) {
        if (!moveRight && permit) {
            moveRight = true;
        }
    }

    @Override
    public synchronized void setUp(boolean permit) {

    }

    @Override
    public synchronized void setDown(boolean permit) {
        forbidDown = permit;
    }

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public double getVelocity() {
        return velocity;
    }

    @Override
    public synchronized void moveRight() {
        if (moveRight && forbidDown) {
            x += velocity;
        }
    }

    @Override
    public synchronized void moveLeft() {
        if (!moveRight && forbidDown) {
            x -= velocity;
        }
    }

    @Override
    public void moveUp() {

    }

    @Override
    public synchronized void moveDown() {
        if (!forbidDown) {
            y += velocity;
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return image.getImage().getWidth();
    }

    @Override
    public double getHeight() {
        return image.getImage().getHeight();
    }

    @Override
    public Node getNode() {
        return node;
    }

    @Override
    public ImageView getImage() {
        return image;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public synchronized void uptHit() {
        if (active) {
            active = false;
            SoundManager.playSound(Sound.BUMP);
            action = Action.NOTHING;
            changeImage();
        }
    }

    @Override
    public void setLevelController(LevelController levelController) {

    }

    private void changeImage() {
        double before = image.getImage().getHeight();
        image.setImage(new Image(Texture.class
                .getClassLoader().getResource("textures/enemy_destroyed.png")
                .toExternalForm()));
        y += (image.getImage().getHeight() + before - 20);
    }

    @Override
    public synchronized Action getAction() {
        return action;
    }
}
