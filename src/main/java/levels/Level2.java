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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Level2 implements Level {
    private List<Element> textures;
    private List<Hittable> hittables;
    private List<Movable> enemies;
    private List<ActionElement> actionElements;

    private double heroStartX;
    private double heroStartY;
    private int ground = 800;

    private double length;

    public Level2() {
        this.heroStartX = 400;
        this.heroStartY = 500;
        initTextures();
        initElements();
        initEnemies();

        this.length = 10500;
    }

    private void initElements() {
        actionElements = new CopyOnWriteArrayList<>();
        actionElements.add(new MushroomBlock(950, ground - 500));
        actionElements.add(new CoinBlock(850, ground - 500));
        actionElements.add(new CoinBlock(1000, ground - 500));
        actionElements.add(new CoinBlock(1100, ground - 500));
        actionElements.add(new CoinBlock(4500, ground - 400));
        actionElements.add(new CoinBlock(4550, ground - 400));
        actionElements.add(new CoinBlock(4600, ground - 400));
        actionElements.add(new CoinBlock(4650, ground - 400));
        actionElements.add(new CoinBlock(7700, ground - 400));
        actionElements.add(new CoinBlock(7900, ground - 400));
        actionElements.add(new CoinBlock(8000, ground - 400));
        actionElements.add(new CoinBlock(8200, ground - 400));
    }

    @Override
    public void setLevelController(LevelController levelController) {
        for (ActionElement a :
                actionElements) {
            a.setLevelController(levelController);
        }
    }

    private void initEnemies() {
        hittables = new CopyOnWriteArrayList<>();
        enemies = new CopyOnWriteArrayList<>();

        addEnemy(new Enemy(1200, 400));
        addEnemy(new Enemy(1400, 400));
        addEnemy(new Enemy(1900, 400));
        addEnemy(new Enemy(2100, 400));
        addEnemy(new Enemy(5200, 100));
        addEnemy(new Enemy(5300, 100));
        addEnemy(new Enemy(5900, -100));
        addEnemy(new Enemy(6000, -100));
        addEnemy(new Enemy(6700, 100));
        addEnemy(new Enemy(6800, 100));
    }

    private void addEnemy(Enemy enemy) {
        hittables.add(enemy);
        enemies.add(enemy);
        actionElements.add(enemy);
    }

    private void initTextures() {
        textures = new CopyOnWriteArrayList<>();
        textures.add(Texture.getBigWall(0, ground));
        textures.add(Texture.getBigWall(500, ground));
        textures.add(Texture.getBigWall(1000, ground));
        textures.add(Texture.getBigWall(1500, ground));
        textures.add(Texture.getBigWall(2000, ground));
        textures.add(Texture.getBigWall(2500, ground));
        textures.add(Texture.getBigWall(3300, ground));
        textures.add(Texture.getBigWall(4200, ground));
        textures.add(Texture.getBigWall(4700, ground));
        textures.add(Texture.getBigWall(7300, ground));
        textures.add(Texture.getBigWall(7800, ground));
        textures.add(Texture.getCubes(8250, ground-200, 4));
        textures.add(Texture.getCubes(8200, ground-150, 3));
        textures.add(Texture.getCubes(8150, ground-100, 2));
        textures.add(Texture.getCubes(8100, ground-50, 1));
        textures.add(Texture.getBigWall(8400, ground));
        textures.add(Texture.getCubes(8550, ground-50, 1));
        textures.add(Texture.getCubes(8500, ground-100, 2));
        textures.add(Texture.getCubes(8450, ground-150, 3));
        textures.add(Texture.getCubes(8400, ground-200, 4));
        textures.add(Texture.getBigWall(8900, ground));
        textures.add(Texture.getCubes(9350, ground-200, 4));
        textures.add(Texture.getCubes(9300, ground-150, 3));
        textures.add(Texture.getCubes(9250, ground-100, 2));
        textures.add(Texture.getCubes(9200, ground-50, 1));
        textures.add(Texture.getBigWall(9700, ground));
        textures.add(Texture.getCubes(9700, ground-200, 4));
        textures.add(Texture.getCubes(9750, ground-150, 3));
        textures.add(Texture.getCubes(9800, ground-100, 2));
        textures.add(Texture.getCubes(9850, ground-50, 1));
        textures.add(Texture.getBigWall(10200, ground));
        textures.add(Texture.getBigWall(10700, ground));
        textures.add(Texture.getCastle(10700, ground - 600));

        textures.add(Texture.getQuestion(800, ground - 500));
        textures.add(Texture.getBrick(900, ground - 500));
        textures.add(Texture.getBrick(5100, ground - 500));
        textures.add(Texture.getCubes(5100, ground - 500 - 50, 1));
        textures.add(Texture.getBrick(5150, ground - 500));
        textures.add(Texture.getBrick(5200, ground - 500));
        textures.add(Texture.getBrick(5250, ground - 500));
        textures.add(Texture.getQuestion(5300, ground - 500));
        textures.add(Texture.getQuestion(5350, ground - 500));
        textures.add(Texture.getBrick(5400, ground - 500));
        textures.add(Texture.getBigBrick(5450, ground - 500));
        textures.add(Texture.getCubes(5550, ground - 500 - 50, 1));
        textures.add(Texture.getBrick(5800, ground - 800));
        textures.add(Texture.getCubes(5800, ground - 800 - 50, 1));
        textures.add(Texture.getBigBrick(5850, ground - 800));
        textures.add(Texture.getBigBrick(6000, ground - 800));
        textures.add(Texture.getBigBrick(6150, ground - 800));
        textures.add(Texture.getCubes(6250, ground - 800 - 50, 1));
        textures.add(Texture.getBrick(6450, ground - 500));
        textures.add(Texture.getCubes(6450, ground - 500 - 50, 1));
        textures.add(Texture.getBigBrick(6500, ground - 500));
        textures.add(Texture.getBigBrick(6650, ground - 500));
        textures.add(Texture.getBigBrick(6800, ground - 500));
        textures.add(Texture.getCubes(6900, ground - 500 - 50, 1));

        textures.add(Texture.getPipe(1300, ground - 190));
        textures.add(Texture.getPipe(2000, ground - 190));
        textures.add(Texture.getPipe(2700, ground - 190));
        textures.add(Texture.getPipe(3400, ground - 190));

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
