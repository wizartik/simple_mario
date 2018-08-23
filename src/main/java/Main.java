import controllers.LevelController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import levels.Level2;
import parameters.Sound;
import sound.SoundManager;

public class Main extends Application {

    private final static int width = 1000;
    private final static int height = 800;

    private AnimationTimer timer;
    private Stage stage;

    private boolean moveLeft;
    private LevelController levelController;
    private boolean moveRight;
    private boolean jump;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("Mario");

        stage.setScene(startWindow());
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        if (levelController != null) {
            levelController.stop();
        }
        SoundManager.stopSoundtrack();
        if (timer != null) {
            timer.stop();
        }
        super.stop();
    }

    private Scene startGame() {

        moveLeft = false;
        moveRight = false;
        jump = false;

        levelController = new LevelController(new Level2(),
                width, height);

        Group root = new Group(levelController.getVisibleNodes());

        root.getChildren().add(levelController.getHeroNode());

        Scene scene = new Scene(root, width, height);
        Image background = new Image(this.getClass().getClassLoader()
                .getResource("textures/background.jpg").toExternalForm());

        SoundManager.playBackground(Sound.SOUNDTRACK);
        scene.setFill(new ImagePattern(background));
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case SPACE:
                        jump = true;
                        break;
                    case LEFT:
                        moveLeft = true;
                        break;
                    case RIGHT:
                        moveRight = true;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case LEFT: case RIGHT:
                        moveRight = false;
                        moveLeft = false;
                        break;
                    case SPACE:
                        jump = false;
                        break;
                }
            }
        });

        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                levelController.refresh();
                root.getChildren().removeAll(levelController.getInvisibleElements());
                levelController.addNew(root.getChildren());

                if (levelController.isLoss()){
                    stage.setScene(gameOverWindow());
                    levelController.stop();
                    timer.stop();
                } else if (levelController.isVictory()){
                    stage.setScene(victoryWindow());
                    levelController.stop();
                    timer.stop();
                }
                processMove();
            }
        };
        timer.start();
        return scene;
    }

    private Scene startWindow(){
        StackPane root = new StackPane();
        Text text = new Text("PRESS ENTER\nTO START GAME");
        Text text2 = new Text("use arrows and space bar");
        text.setFont(Font.font("Tahoma", 100));
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);
        text2.setFont(Font.font("Tahoma", 40));
        text2.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text2);
        StackPane.setAlignment(text2, Pos.BOTTOM_CENTER);

        Scene scene = new Scene(root, width, height, Color.CORNFLOWERBLUE);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        stage.setScene(startGame());
                        break;
                }
            }
        });

        return scene;
    }

    private Scene gameOverWindow(){
        StackPane root = new StackPane();
        Text text = new Text("YOU LOST\nPRESS ENTER");
        text.setFont(Font.font("Tahoma", 100));
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        Scene scene = new Scene(root, width, height, Color.DARKSLATEGREY);

        SoundManager.stopSoundtrack();
        SoundManager.playSound(Sound.LOSS);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        stage.setScene(startWindow());
                        break;
                }
            }
        });

        return scene;
    }

    private Scene victoryWindow(){
        StackPane root = new StackPane();
        Text text = new Text("YOU WON\nPRESS ENTER");
        text.setFont(Font.font("Tahoma", 100));
        text.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(text);
        StackPane.setAlignment(text, Pos.CENTER);

        Scene scene = new Scene(root, width, height, Color.LIGHTBLUE);

        SoundManager.stopSoundtrack();
        SoundManager.playSound(Sound.VICTORY);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ENTER:
                        stage.setScene(startWindow());
                        break;
                }
            }
        });

        return scene;
    }

    private void processMove(){
        if (moveLeft){
            levelController.moveLeft();
        }

        if (moveRight){
            levelController.moveRight();
        }

        if (!moveRight && !moveLeft){
            levelController.stopRunning();
        }

        if (jump){
            levelController.jump();
        }
    }
}
