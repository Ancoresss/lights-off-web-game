package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Score;
import org.junit.Test;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

public class ScoreServiceTest {
    private ScoreService createService() {
        return new ScoreServiceJDBC();
    }

    @Test
    public void testReset() {
        ScoreService scoreService = createService();
        scoreService.reset();
        assertEquals(0, scoreService.getTopScores("lightsOff").size());
    }

    @Test
    public void testAddScore() {
        ScoreService scoreService = createService();
        scoreService.reset();
        Date date = new Date();
        scoreService.addScore(new Score("lightsOff", "nameTest1", 23, 2, 1, date));
        List<Score> scores = scoreService.getTopScores("lightsOff");

        assertEquals(1, scores.size());

        assertEquals("lightsOff", scores.get(0).getGame());
        assertEquals("nameTest1", scores.get(0).getPlayer());
        assertEquals(23, scores.get(0).getPoints());
        assertEquals(2, scores.get(0).getMinutesOfPlaying());
        assertEquals(1, scores.get(0).getLevel());
        assertEquals(date, scores.get(0).getPlayedAt());
    }

    @Test
    public void testAdd3PersonsScore() {
        ScoreService scoreService = createService();
        scoreService.reset();
        Date date = new Date();

        scoreService.addScore(new Score("lightsOff", "nameTest1", 90, 2, 1, date));
        scoreService.addScore(new Score("lightsOff", "nameTest2", 34, 4, 2, date));
        scoreService.addScore(new Score("lightsOff", "nameTest3", 23, 5, 3, date));
        List<Score> scores = scoreService.getTopScores("lightsOff");

        assertEquals(3, scores.size());

        assertEquals("lightsOff", scores.get(0).getGame());
        assertEquals("nameTest1", scores.get(0).getPlayer());
        assertEquals(90, scores.get(0).getPoints());
        assertEquals(2, scores.get(0).getMinutesOfPlaying());
        assertEquals(1, scores.get(0).getLevel());
        assertEquals(date, scores.get(0).getPlayedAt());

        assertEquals("lightsOff", scores.get(1).getGame());
        assertEquals("nameTest2", scores.get(1).getPlayer());
        assertEquals(34, scores.get(1).getPoints());
        assertEquals(4, scores.get(1).getMinutesOfPlaying());
        assertEquals(2, scores.get(1).getLevel());
        assertEquals(date, scores.get(1).getPlayedAt());

        assertEquals("lightsOff", scores.get(2).getGame());
        assertEquals("nameTest3", scores.get(2).getPlayer());
        assertEquals(23, scores.get(2).getPoints());
        assertEquals(5, scores.get(2).getMinutesOfPlaying());
        assertEquals(3, scores.get(2).getLevel());
        assertEquals(date, scores.get(2).getPlayedAt());
    }

    @Test
    public void testAdd10PersonsScore() {
        ScoreService scoreService = createService();
        scoreService.reset();
        for (int i = 0; i < 10; i++)
            scoreService.addScore(new Score("lightsOff", "nameTest1", 90, 2, 1, new Date()));
        assertEquals(10, scoreService.getTopScores("lightsOff").size());
    }
}
