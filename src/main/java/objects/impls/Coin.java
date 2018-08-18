package objects.impls;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.interfaces.Movable;
import parameters.Sound;
import sound.SoundManager;

public class Coin implements Movable {
    private double x;
    private double y;

    private double velocity = 5;

    private boolean active;

    private boolean moveRight;

    private boolean visible;

    private Node node;
    private ImageView image;

    public Coin(double x, double y) {
        this.x = x;
        this.y = y;

        active = true;
        visible = true;

        image = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/coin.png")
                .toExternalForm()));

        moveRight = true;
        node = image;

        SoundManager.playSound(Sound.COIN);
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
    }

    @Override
    public void moveLeft() {
    }


    private int life = 40;

    @Override
    public void moveUp() {
        if (life-- > 0) {
            y -= velocity;
        } else {
            active = false;
            visible = false;
        }
    }

    @Override
    public void moveDown() {
    }

    @Override
    public void stop() {
    }
}
