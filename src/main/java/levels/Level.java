package levels;

import controllers.LevelController;
import objects.interfaces.ActionElement;
import objects.interfaces.Element;
import objects.interfaces.Hittable;
import objects.interfaces.Movable;

import java.util.List;

public interface Level {

    List<Element> getTextures();
    List<Hittable> getHittables();
    List<Movable> getEnemies();
    List<ActionElement> getActionElements();

    double getHeroStartX();
    double getHeroStartY();

    double getLength();

    void setLevelController(LevelController levelController);

}
