package sk.tuke.gamestudio.entity;

import java.io.Serializable;

public class ScoreID implements Serializable {
    private String player;
    private String game;

    public ScoreID() {
    }

    public ScoreID(String player, String game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreID scoreID = (ScoreID) o;

        if (player != null ? !player.equals(scoreID.player) : scoreID.player != null) return false;
        return game != null ? game.equals(scoreID.game) : scoreID.game == null;
    }

    @Override
    public int hashCode() {
        int result = player != null ? player.hashCode() : 0;
        result = 31 * result + (game != null ? game.hashCode() : 0);
        return result;
    }
}
