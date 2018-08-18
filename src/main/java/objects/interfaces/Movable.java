package objects.interfaces;

public interface Movable extends Element {

    double getVelocity();

    void moveRight();
    void moveLeft();
    void moveUp();
    void moveDown();
    void stop();

}
