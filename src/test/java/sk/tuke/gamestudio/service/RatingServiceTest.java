package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingServiceTest {
    private RatingService createService() {
        return new RatingServiceJDBC();
    }

    @Test
    public void testReset() {
        RatingService ratingService = createService();
        ratingService.reset();
        assertEquals(0, ratingService.getAllRatings("lightsOff"));
    }

    @Test
    public void testSetAndGetRating() {
        RatingService ratingService = createService();
        ratingService.reset();
        Date date = new Date();
        ratingService.setRating(new Rating("lightsOff", "testName1", 2, date));

        assertEquals(1, ratingService.getAllRatings("lightsOff"));
        assertEquals(2, ratingService.getRating("lightsOff", "testName1"));
    }

    @Test
    public void testSet3Ratings() {
        RatingService ratingService = createService();
        ratingService.reset();
        Date date = new Date();
        ratingService.setRating(new Rating("lightsOff", "testName1", 3, date));
        ratingService.setRating(new Rating("lightsOff", "testName2", 4, date));

        assertEquals(2, ratingService.getAllRatings("lightsOff"));
        assertEquals(3, ratingService.getRating("lightsOff", "testName1"));
        assertEquals(4, ratingService.getRating("lightsOff", "testName2"));
    }

    @Test
    public void testSet10Ratings() {
        RatingService ratingService = createService();
        ratingService.reset();
        for(int i = 0; i < 10; i++)
            ratingService.setRating(new Rating("lightsOff", "testName1", 3, new Date()));
        assertEquals(10, ratingService.getAllRatings("lightsOff"));
    }

    @Test
    public void testGetAverageRating() {
        RatingService ratingService = createService();
        ratingService.reset();
        ratingService.setRating(new Rating("lightsOff", "testName1", 3, new Date()));
        ratingService.setRating(new Rating("lightsOff", "testName2", 4, new Date()));
        ratingService.setRating(new Rating("lightsOff", "testName3", 1, new Date()));
        ratingService.setRating(new Rating("lightsOff", "testName4", 2, new Date()));
        ratingService.setRating(new Rating("lightsOff", "testName5", 5, new Date()));
        assertEquals(3, ratingService.getAverageRating("lightsOff"));
    }
}
