package objects.impls;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.interfaces.Hittable;
import objects.interfaces.Movable;
import parameters.Action;
import parameters.Settings;
import parameters.Sound;
import sound.SoundManager;

public class Hero implements Movable, Hittable {

    private double x;
    private double y;
    private ImageView image;
    private boolean active;
    private double velocity;
    private Node node;

    private Image runRight;
    private Image runLeft;
    private Image jumpRight;
    private Image jumpLeft;
    private Image standRight;
    private Image standLeft;

    private final byte DIRECTION_RIGHT = 0;
    private final byte DIRECTION_LEFT = 1;

    private boolean movingUp;
    private boolean movingDown;
    private boolean running;

    private boolean forbidRight;
    private boolean forbidLeft;
    private boolean forbidDown;
    private boolean forbidUp;

    private byte direction;

    public Hero(double x, double y) {
        this.x = x;
        this.y = y;

        initImages();
        node = image;
        node.relocate(x, y);

        velocity = Settings.HERO_VELOCITY;
        active = true;
    }

    @Override
    public Node getNode() {
        return node;
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
    public ImageView getImage() {
        return image;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public double getVelocity() {
        return velocity;
    }

    @Override
    public boolean isMoving() {
        return movingDown || movingUp || running;
    }


    private void initImages() {
        image = new ImageView();

        decrease();
        image.setImage(standRight);
    }

    @Override
    public void moveRight() {
        direction = DIRECTION_RIGHT;
        running = true;
        if (!forbidRight) {
            this.x += velocity;
            changeImage();
        }
    }

    @Override
    public void moveLeft() {
        direction = DIRECTION_LEFT;
        running = true;
        if (!forbidLeft) {
            this.x -= velocity;
            changeImage();
        }
    }

    @Override
    public void moveUp() {
        if (!forbidUp) {
            this.y -= velocity;
            changeImage();
        }
        movingUp = !forbidUp;
    }

    @Override
    public void moveDown() {
        if (!forbidDown) {
            this.y += velocity;
        }
        movingDown = !forbidDown;
        changeImage();
    }

    public void moveUp(double dy) {
        if (!forbidUp) {
            this.y -= dy;
            changeImage();
        }
        movingUp = !forbidUp;
    }

    public void moveDown(double dy) {

        if (!forbidDown) {
            this.y += dy;
            changeImage();
        }
        movingDown = !forbidDown;
    }

    @Override
    public void stop() {
        running = false;
        movingDown = false;
        movingUp = false;
        changeImage();
    }

    public void stopJump() {
        movingDown = false;
        movingUp = false;
        changeImage();
    }

    private void changeImage() {
        if (direction == DIRECTION_RIGHT) {
            if (movingUp || movingDown) {
                image.setImage(jumpRight);
            } else {
                image.setImage(running ? runRight : standRight);
            }
        } else {
            if (movingUp || movingDown) {
                image.setImage(jumpLeft);
            } else {
                image.setImage(running ? runLeft : standLeft);
            }
        }
        node = image;
    }

    @Override
    public synchronized void setRight(boolean permit) {
        forbidRight = permit;
    }

    @Override
    public synchronized void setLeft(boolean permit) {
        forbidLeft = permit;
    }

    @Override
    public synchronized void setUp(boolean permit) {
        forbidUp = permit;
    }

    @Override
    public synchronized void setDown(boolean permit) {
        forbidDown = permit;
    }

    public synchronized void doAction(Action action) {
        switch (action) {
            case DIE:
                die();
                break;
            case GROW:
                grow();
                break;
            case DECREASE:
                break;
        }
    }

    private void die() {
        active = false;
        movingDown = false;
        movingUp = false;
    }

    private void grow() {

        this.runRight = new Image(this.getClass().getClassLoader()
                .getResource("mario/run_right.gif").toExternalForm());

        y -= runRight.getHeight() - runLeft.getHeight();

        this.runLeft = new Image(this.getClass().getClassLoader()
                .getResource("mario/run_left.gif").toExternalForm());

        this.standRight = new Image(this.getClass().getClassLoader()
                .getResource("mario/stand_right.png").toExternalForm());

        this.standLeft = new Image(this.getClass().getClassLoader()
                .getResource("mario/stand_left.png").toExternalForm());

        this.jumpRight = new Image(this.getClass().getClassLoader()
                .getResource("mario/jump_right.png").toExternalForm());

        this.jumpLeft = new Image(this.getClass().getClassLoader()
                .getResource("mario/jump_left.png").toExternalForm());

        changeImage();
        SoundManager.playSound(Sound.POWERUP);
    }

    private void decrease() {
        this.runRight = new Image(this.getClass().getClassLoader()
                .getResource("mario/small_run_right.gif").toExternalForm());

        this.runLeft = new Image(this.getClass().getClassLoader()
                .getResource("mario/small_run_left.gif").toExternalForm());

        this.standRight = new Image(this.getClass().getClassLoader()
                .getResource("mario/small_stand_right.png").toExternalForm());

        this.standLeft = new Image(this.getClass().getClassLoader()
                .getResource("mario/small_stand_left.png").toExternalForm());

        this.jumpRight = new Image(this.getClass().getClassLoader()
                .getResource("mario/small_jump_right.png").toExternalForm());

        this.jumpLeft = new Image(this.getClass().getClassLoader()
                .getResource("mario/small_jump_left.png").toExternalForm());
        changeImage();
    }
}
