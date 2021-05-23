package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQuery( name = "Score.addScore",
        query = "SELECT s FROM Score s WHERE s.game=:game and s.player=:player")
@NamedQuery( name = "Score.getTopScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")
@NamedQuery( name = "Score.resetScores",
        query = "DELETE FROM Score")
@IdClass(ScoreID.class)
public class Score {
    @Id
    private String game;
    @Id
    private String player;

    private int points;

    private long minutesOfPlaying;

    private int level;

    private Date playedAt;

    public Score(String game, String player, int points, long minutesOfPlaying, int level, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.minutesOfPlaying = minutesOfPlaying;
        this.level = level;
        this.playedAt = playedOn;
    }

    public Score(String game, String player, int points, int level, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.level = level;
        this.playedAt = playedOn;
    }

    public Score() {
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

    public int getPoints() {
        return points;
    }

    public Date getPlayedAt() {
        return playedAt;
    }

    public long getMinutesOfPlaying() {
        return minutesOfPlaying;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", points=" + points +
                ", playedOn=" + playedAt +
                '}';
    }

}
