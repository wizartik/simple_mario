package objects.interfaces;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

public interface Element {

    double getX();
    double getY();

    double getWidth();
    double getHeight();

    Node getNode();

    ImageView getImage();

    boolean isActive();
    boolean isVisible();
}
