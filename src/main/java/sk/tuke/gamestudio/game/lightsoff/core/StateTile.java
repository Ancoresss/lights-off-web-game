package sk.tuke.gamestudio.game.lightsoff.core;

public enum StateTile {
    ON(1),
    OFF(0);

    private int value;

    StateTile(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
