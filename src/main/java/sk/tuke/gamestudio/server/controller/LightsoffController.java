package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.lightsoff.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.lightsoff.core.Logic;
import sk.tuke.gamestudio.game.lightsoff.core.StateTile;
import sk.tuke.gamestudio.game.lightsoff.core.StatusGame;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/lightsoff")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class LightsoffController {
    @Autowired
    private UserController userController;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    private Player player; //user, who start to play game; if player == null, then he don't start the game
    private final ConsoleUI consoleUI = new ConsoleUI();
    private final Logic logic = new Logic(consoleUI);
    private int[][] field;

    private StatusGame statusGame = StatusGame.PLAYING;
    public static int moves;

    @GetMapping
    public String startGame(@RequestParam(required = false) String row, @RequestParam(required = false) String column, Model model) {
        if(userController.getLoggedPlayer() == null) {
            return "error";
        } else if(userController.getLoggedPlayer() != null && player == null) {
            model.addAttribute("messageError", "This will not work. You have to start new game.");
            return "error";
        }
        if(userController.isLogged() && userController.getLoggedPlayer().getNickname().equals(player.getNickname())) {
            if(statusGame == StatusGame.PLAYING) {
                if(row != null && column != null) {
                    logic.switchStars(field, Integer.parseInt(row), Integer.parseInt(column), consoleUI.getColumns());
                }
            }
            statusGame = statusOfGame();
            if(statusGame == StatusGame.WIN) {
                model.addAttribute("messageGame", "You are win :D");
            } else if (statusGame == StatusGame.LOSE) {
                model.addAttribute("messageGame", "You are lose :(");
            }

            model.addAttribute("moves", getMoves());
            model.addAttribute("statusGame", statusGame);
            model.addAttribute("player", player);
            return "game";
        } else {
            model.addAttribute("messageError", "This will not work. You have to start new game.");
            return "error";
        }
    }

    @GetMapping("/new")
    public String newGame(Model model) {
        moves = -1;
        statusGame = StatusGame.PLAYING;
        player = userController.getLoggedPlayer();
        if(userController.isLogged()) {
            consoleUI.convertLevel(player.getLevel());
            model.addAttribute("player", player);
            field = logic.generateField();
            return "redirect:/lightsoff";
        } 
        else {
            return "error";
        }
    }

    @PostMapping("/review")
    public String review(String rate, String comment) {
        scoreService.addScore(new Score("lightsoff", player.getNickname(), 101 - moves, player.getLevel(), new Date()));
        ratingService.setRating(new Rating("lightsoff", player.getNickname(), Integer.parseInt(rate), new Date()));
        commentService.addComment(new Comment("lightsoff", player.getNickname(), comment, new Date()));
        return "redirect:/";
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for(int i = 0; i < consoleUI.getRows(); i++) {
            sb.append("<tr>\n");
            for(int j = 0; j < consoleUI.getColumns(); j++) {
                sb.append("<td>");
                sb.append(String.format("<a href='/lightsoff?row=%d&column=%d'>", i, j));
                if(field[i][j] == StateTile.ON.getValue()) {
                    sb.append("<img src='/img/lamp_on.png'>\n");
                } else {
                    sb.append("<img src='/img/lamp_off.png'>\n");
                }
                sb.append("</a>");
                sb.append("</td>");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>\n");
        return sb.toString();
    }

    private StatusGame statusOfGame() {
        boolean isAllLightsAreOff = logic.allLightsAreOff(field, consoleUI);
        if(isAllLightsAreOff) {
            return StatusGame.WIN;
        } else if(moves == 100) {
            return StatusGame.LOSE;
        } else {
            moves++;
            return StatusGame.PLAYING;
        }
    }

    public static int getMoves() {
        return moves;
    }
}
