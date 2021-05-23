package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Player;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.PlayerService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.List;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    private Player loggedPlayer;
    private boolean userExist = false;
    private boolean validUser = true;
    private boolean isLogged = false;

    private List<Score> scoreList;
    private Integer averageRating;
    private Integer userRating;
    private List<Comment> commentList;

    @GetMapping("/")
    public String index(Model model) {
        scoreList = scoreService.getTopScores("lightsoff");
        averageRating = ratingService.getAverageRating("lightsoff");
        try {
            userRating = ratingService.getRating("lightsoff", loggedPlayer.getNickname());
        } catch (Exception e) {
            userRating = null;
        }
        commentList = commentService.getComments("lightsoff");

        model.addAttribute("player", getLoggedPlayer());
        model.addAttribute("isLogged", isLogged());
        model.addAttribute("topScores", scoreList);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("userRating", userRating);
        model.addAttribute("commentPlayers", commentList);
        return "index";
    }

    @PostMapping("/level")
    public String getLevel(String level) {
        loggedPlayer.setLevel(Integer.parseInt(level));
        return "redirect:/lightsoff/new";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        Player player = new Player();
        model.addAttribute("isLogged", isLogged());
        model.addAttribute("player", player);
        model.addAttribute("isValidUser", isValidUser());
        validUser = true;
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute("player") Player player) {
        Player dbPlayer = playerService.getPlayerByNickname(player.getNickname());
        if(dbPlayer != null) {
            if(player.getPassword().equals(dbPlayer.getPassword())) {
                loggedPlayer = new Player(dbPlayer.getId(), dbPlayer.getNickname(), dbPlayer.getPassword(), dbPlayer.getLevel());
                isLogged = true;
                return "redirect:/";
            }
        }
        validUser = false;
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Player player = new Player();
        model.addAttribute("isLogged", isLogged());
        model.addAttribute("player", player);
        model.addAttribute("isUserExist", isUserExist());
        userExist = false;
        return "register";
    }

    @PostMapping("/register/new")
    public String newPlayer(@ModelAttribute("player") Player player) {
        if(playerService.getPlayerByNickname(player.getNickname()) != null) {
            userExist = true;
            return "redirect:/register";
        }
        playerService.addPlayer(player);
        return "redirect:/login";
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        isLogged = false;
        loggedPlayer = null;
        return "redirect:/login";
    }

    @RequestMapping(value = "/delete")
    public String delete() {
        if(isLogged) {
            playerService.deletePlayer(getLoggedPlayer());
            isLogged = false;
        }
        return "redirect:/login";
    }

    public boolean isUserExist() {
        return userExist;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public boolean isLogged() { return isLogged; }

    public Player getLoggedPlayer() {
        return loggedPlayer;
    }
}
