package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery( name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game=:game")
@NamedQuery( name = "Rating.getRating",
        query = "SELECT r FROM Rating r WHERE r.game=:game AND r.player=:player")
@NamedQuery( name = "Rating.getAllRating",
        query = "SELECT COUNT(r) FROM Rating r WHERE r.game=:game")
@NamedQuery( name = "Rating.resetRating",
        query = "DELETE FROM Rating")
@IdClass(RatingID.class)
public class Rating {
    @Id
    private String game;
    @Id
    private String player;

    private int rating;

    private Date ratedOn;

    public Rating(String game, String player, int rating, Date date) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = date;
    }

    public Rating() {
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setRatedOn(Date date) {
        this.ratedOn = date;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", date=" + ratedOn +
                '}';
    }
}
