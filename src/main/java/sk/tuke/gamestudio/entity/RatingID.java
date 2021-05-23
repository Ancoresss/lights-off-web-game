package sk.tuke.gamestudio.entity;

import java.io.Serializable;

public class RatingID implements Serializable {
    private String game;
    private String player;

    public RatingID() {
    }

    public RatingID(String game, String player) {
        this.game = game;
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingID ratingID = (RatingID) o;

        if (game != null ? !game.equals(ratingID.game) : ratingID.game != null) return false;
        return player != null ? player.equals(ratingID.player) : ratingID.player == null;
    }

    @Override
    public int hashCode() {
        int result = game != null ? game.hashCode() : 0;
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}
