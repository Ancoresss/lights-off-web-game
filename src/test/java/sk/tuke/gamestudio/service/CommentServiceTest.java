package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import org.junit.Test;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentServiceJDBC;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CommentServiceTest {
    private CommentService createService() {
        return new CommentServiceJDBC();
    }

    @Test
    public void testReset() {
        CommentService commentService = createService();
        commentService.reset();
        assertEquals(0, commentService.getComments("lightsOff").size());
    }

    @Test
    public void testAddComment() {
        CommentService commentService = createService();
        commentService.reset();
        Date date = new Date();
        commentService.addComment(new Comment("lightsOff", "nameTest1", "First comment for lightsOff", date));
        List<Comment> comments = commentService.getComments("lightsOff");

        assertEquals(1, comments.size());
        assertEquals("lightsOff", comments.get(0).getGame());
        assertEquals("nameTest1", comments.get(0).getPlayer());
        assertEquals("First comment for lightsOff", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());
    }

    @Test
    public void testAdd3PersonsScore() {
        CommentService commentService = createService();
        commentService.reset();
        Date date = new Date();

        commentService.addComment(new Comment("lightsOff", "nameTest1", "First comment for lightsOff", date));
        commentService.addComment(new Comment("lightsOff", "nameTest2", "Second comment for lightsOff", date));
        commentService.addComment(new Comment("lightsOff", "nameTest3", "Third comment for lightsOff", date));
        List<Comment> comments = commentService.getComments("lightsOff");

        assertEquals(3, comments.size());

        assertEquals("lightsOff", comments.get(0).getGame());
        assertEquals("nameTest1", comments.get(0).getPlayer());
        assertEquals("First comment for lightsOff", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());

        assertEquals("lightsOff", comments.get(1).getGame());
        assertEquals("nameTest2", comments.get(1).getPlayer());
        assertEquals("Second comment for lightsOff", comments.get(1).getComment());
        assertEquals(date, comments.get(1).getCommentedOn());

        assertEquals("lightsOff", comments.get(2).getGame());
        assertEquals("nameTest3", comments.get(2).getPlayer());
        assertEquals("Third comment for lightsOff", comments.get(2).getComment());
        assertEquals(date, comments.get(2).getCommentedOn());
    }

    @Test
    public void testAdd10PersonsScore() {
        CommentService commentService = createService();
        commentService.reset();
        for (int i = 0; i < 10; i++)
            commentService.addComment(new Comment("lightsOff", "nameTest1", "First comment for lightsOff", new Date()));
        assertEquals(10, commentService.getComments("lightsOff").size());
    }
}
