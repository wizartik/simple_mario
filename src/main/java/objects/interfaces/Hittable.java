package objects.interfaces;

import parameters.Settings;

public interface Hittable extends Element {

    default boolean checkRight(Element obj) {

        double y1 = getY() + getHeight();
        double y2 = obj.getY() + obj.getHeight();

        double x1 = getX() + getWidth();
        double x2 = obj.getX() + obj.getWidth();

        boolean join = ((obj.getY() >= getY() && obj.getY() <= y1)
                || (y2 >= getY() && y2 <= y1));

        return (((getY() > obj.getY() && getY() < y2) || (y1 > obj.getY() &&
                y1 < y2)) || join)
                && ((x1 + Settings.HERO_VELOCITY >= obj.getX()) && (getX() < obj.getX()));
    }

    default boolean checkLeft(Element obj) {
        double y1 = getY() + getHeight();
        double y2 = obj.getY() + obj.getHeight();

        double x1 = getX() + getWidth();
        double x2 = obj.getX() + obj.getWidth();

        boolean join = ((obj.getY() >= getY() && obj.getY() <= y1)
                || (y2 >= getY() && y2 <= y1));

        return (((getY() > obj.getY() && getY() < y2) || (y1 > obj.getY() &&
                y1 < y2)) || join)
                && ((x2 + Settings.HERO_VELOCITY >= getX()) && (x1 > x2));
    }

    default boolean checkUp(Element obj) {
        double x1 = getX() + getWidth();
        double x2 = obj.getX() + obj.getWidth();

        double y1 = getY() + getHeight();
        double y2 = obj.getY() + obj.getHeight();

        boolean join = (obj.getX() + Settings.HERO_VELOCITY >= getX() && obj.getX() <= x1)
                || (x2 + Settings.HERO_VELOCITY >= getX() && x2 <= x1);

        return (((getX() > obj.getX() && getX() < x2) || (x1 > obj.getX() &&
                x1 < x2)) || join) && ((getY() <= y2 + Settings.HERO_VELOCITY) && (y1 > y2));
    }

    default boolean checkDown(Element obj) {
        double x1 = getX() + getWidth();
        double x2 = obj.getX() + obj.getWidth();

        double y1 = getY() + getHeight();
        double y2 = obj.getY() + obj.getHeight();

        boolean join = (obj.getX() + Settings.HERO_VELOCITY >= getX() && obj.getX() <= x1)
                || (x2 + Settings.HERO_VELOCITY >= getX() && x2 <= x1);

        return (((getX() > obj.getX() && getX() < x2) || (x1 > obj.getX() &&
                x1 < x2)) || join) && (y1 + Settings.HERO_VELOCITY > obj.getY() && getY() < y2);
    }

    void setRight(boolean permit);

    void setLeft(boolean permit);

    void setUp(boolean permit);

    void setDown(boolean permit);

    boolean isMoving();
}
