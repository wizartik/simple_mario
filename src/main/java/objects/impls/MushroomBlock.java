package objects.impls;

import controllers.LevelController;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.interfaces.ActionElement;
import parameters.Action;

public class MushroomBlock implements ActionElement {

    private double x;
    private double y;

    private boolean active;
    private Node node;
    private ImageView image;
    private LevelController levelController;
    private boolean done;

    public MushroomBlock(double x, double y) {
        this.x = x;
        this.y = y;

        active = true;
        image = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/question.png")
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
        return true;
    }

    @Override
    public void downHit() {
        if (!done) {
            done = true;
            changeImage();
            levelController.spawnEnemy(new MovingMushroom(x, y - 150));
        }
    }

    private void changeImage(){
        image.setImage(new Image(Texture.class
                .getClassLoader().getResource("textures/question_used.png")
                .toExternalForm()));
    }

    @Override
    public Action getAction() {
        return Action.NOTHING;
    }
}
