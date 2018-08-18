package parameters;

public enum Sound {
    SOUNDTRACK("sounds/soundtrack.wav"),
    BUMP("sounds/bump.wav"),
    COIN("sounds/coin.wav"),
    JUMP("sounds/jump.wav"),
    POWERUP("sounds/powerup.wav"),
    POWERUP_APPEARS("sounds/powerup_appears.wav"),
    VICTORY("sounds/victory.wav"),
    LOSS("sounds/loss.wav");

    private String path;

    Sound(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
