package objects.impls;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.interfaces.Element;

public class Texture implements Element {

    private double x;
    private double y;
    private Node node;

    private ImageView image;

    private boolean active;
    private double width;
    private double height;

    private Texture(double x, double y, ImageView image, boolean active) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.active = active;

        node = image;
        node.relocate(x, y);

        this.width = image.getImage().getWidth();
        this.height = image.getImage().getHeight();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
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

    public static Texture getPipe(double x, double y) {
        ImageView imageView = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/pipe.png")
                .toExternalForm()));
        return new Texture(x, y, imageView, true);
    }

    public static Texture getBigWall(double x, double y) {
        ImageView imageView = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/wall_big.png")
                .toExternalForm()));
        return new Texture(x, y, imageView, true);
    }

    public static Texture getBrick(double x, double y) {
        ImageView imageView = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/brick.png")
                .toExternalForm()));
        return new Texture(x, y, imageView, true);
    }

    public static Texture getBigBrick(double x, double y) {
        ImageView imageView = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/brick_big.png")
                .toExternalForm()));
        return new Texture(x, y, imageView, true);
    }

    public static Texture getQuestion(double x, double y) {
        ImageView imageView = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/question.png")
                .toExternalForm()));
        return new Texture(x, y, imageView, true);
    }

    public static Texture getCastle(double x, double y) {
        ImageView imageView = new ImageView(new Image(Texture.class
                .getClassLoader().getResource("textures/castle.png")
                .toExternalForm()));
        return new Texture(x, y, imageView, true);
    }

    public static Texture getCubes(double x, double y, int qnt) {
        switch (qnt){
            case 1:
                ImageView imageView = new ImageView(new Image(Texture.class
                        .getClassLoader().getResource("textures/cube.png")
                        .toExternalForm()));
                return new Texture(x, y, imageView, true);
            case 2:
                ImageView imageView2 = new ImageView(new Image(Texture.class
                        .getClassLoader().getResource("textures/2cubes.png")
                        .toExternalForm()));
                return new Texture(x, y, imageView2, true);
            case 3:
                ImageView imageView3 = new ImageView(new Image(Texture.class
                        .getClassLoader().getResource("textures/3cubes.png")
                        .toExternalForm()));
                return new Texture(x, y, imageView3, true);
            default:
                ImageView imageView4 = new ImageView(new Image(Texture.class
                        .getClassLoader().getResource("textures/4cubes.png")
                        .toExternalForm()));
                return new Texture(x, y, imageView4, true);
        }
    }
}
