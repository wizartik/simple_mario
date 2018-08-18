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

public class MovingMushroom implements Movable, ActionElement, Hittable {

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


    public MovingMushroom(double x, double y) {
        this.x = x;
        this.y = y;

        active = true;
        visible = true;

        image = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/mushroom.png")
                .toExternalForm()));

        moveRight = true;
        node = image;

        SoundManager.playSound(Sound.POWERUP_APPEARS);
    }

    @Override
    public synchronized Action getAction() {
        return Action.GROW;
    }

    @Override
    public double getVelocity() {
        return velocity;
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
    public void moveRight() {
        if (moveRight && forbidDown){
            x += velocity;
        }
    }

    @Override
    public void moveLeft() {
        if (!moveRight && forbidDown){
            x-=velocity;
        }
    }

    @Override
    public synchronized void moveUp() {

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
    public void setUp(boolean permit) {

    }


    @Override
    public synchronized void setDown(boolean permit) {
        forbidDown = permit;
    }

    @Override
    public synchronized boolean isMoving() {
        return moving;
    }

    @Override
    public synchronized void rightHit() {
        active = false;
        visible = false;
    }

    @Override
    public synchronized void leftHit() {
        active = false;
        visible = false;
    }

    @Override
    public synchronized void uptHit() {
        active = false;
        visible = false;
    }

    @Override
    public synchronized void downHit() {
        active = false;
        visible = false;
    }

    @Override
    public void setLevelController(LevelController levelController) {
    }
}
