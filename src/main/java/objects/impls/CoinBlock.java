package objects.impls;

import controllers.LevelController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.interfaces.ActionElement;
import parameters.Action;

public class CoinBlock implements ActionElement {

    private double x;
    private double y;

    private int coins;

    private boolean active;
    private boolean visible;

    private Node node;
    private ImageView image;
    private LevelController levelController;
    private boolean done;

    public CoinBlock(double x, double y) {
        this.x = x;
        this.y = y;

        active = true;
        visible = true;

        image = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/brick.png")
                .toExternalForm()));

        node = image;
    }

    public void setLevelController(LevelController levelController) {
        this.levelController = levelController;
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
    public void downHit() {
        if (!done) {
            done = true;
            levelController.doAction(new Coin(x, y - 50));
        }
    }

    @Override
    public Action getAction() {
        return Action.NOTHING;
    }

}
