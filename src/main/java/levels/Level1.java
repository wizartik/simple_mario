package levels;

import controllers.LevelController;
import objects.interfaces.ActionElement;
import objects.interfaces.Element;
import objects.interfaces.Hittable;
import objects.interfaces.Movable;
import objects.impls.CoinBlock;
import objects.impls.Enemy;
import objects.impls.MushroomBlock;
import objects.impls.Texture;

import java.util.ArrayList;
import java.util.List;

public class Level1 implements Level {

    private List<Element> textures;
    private List<Hittable> hittables;
    private List<Movable> enemies;
    private List<ActionElement> actionElements;

    private double heroStartX;
    private double heroStartY;

    private double length;

    public Level1() {
        this.heroStartX = 400;
        this.heroStartY = 0;
        initTextures();
        initElements();
        initEnemies();

        this.length = 3000;
    }

    private void initElements() {
        actionElements = new ArrayList<>();
        actionElements.add(new MushroomBlock(250, 400));
        actionElements.add(new CoinBlock(200,400));
    }

    @Override
    public void setLevelController(LevelController levelController){
        for (ActionElement a :
                actionElements) {
            a.setLevelController(levelController);
        }
    }

    private void initEnemies() {
        hittables = new ArrayList<>();
        enemies = new ArrayList<>();

        addEnemy(new Enemy(1200, 400));
        addEnemy(new Enemy(1400, 400));
    }

    private void addEnemy(Enemy enemy){
        hittables.add(enemy);
        enemies.add(enemy);
        actionElements.add(enemy);
    }

    private void initTextures() {
        textures = new ArrayList<>();
        int ground = 800;
        textures.add(Texture.getBigWall(0, ground));
        textures.add(Texture.getBigWall(1000, ground));
        textures.add(Texture.getBigWall(2000, ground));

        textures.add(Texture.getPipe(600, ground - 200));
        textures.add(Texture.getPipe(1000, ground - 200));
        textures.add(Texture.getPipe(1700, ground - 200));

        textures.add(Texture.getBrick(300, ground - 400));

    }

    @Override
    public List<Element> getTextures() {
        return textures;
    }

    @Override
    public List<Hittable> getHittables() {
        return hittables;
    }

    @Override
    public List<Movable> getEnemies() {
        return enemies;
    }

    @Override
    public List<ActionElement> getActionElements() {
        return actionElements;
    }

    @Override
    public double getHeroStartX() {
        return heroStartX;
    }

    @Override
    public double getHeroStartY() {
        return heroStartY;
    }

    @Override
    public double getLength() {
        return length;
    }

}
