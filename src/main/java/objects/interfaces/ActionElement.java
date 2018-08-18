package objects.interfaces;

import controllers.LevelController;
import parameters.Action;

public interface ActionElement extends Element {

    default void rightHit() {

    }

    default void leftHit() {

    }

    default void uptHit() {

    }

    default void downHit() {

    }

    void setLevelController(LevelController levelController);

    Action getAction();

}
