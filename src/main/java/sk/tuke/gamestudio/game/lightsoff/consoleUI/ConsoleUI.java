package sk.tuke.gamestudio.game.lightsoff.consoleUI;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.lightsoff.core.*;
import sk.tuke.gamestudio.entity.*;
import sk.tuke.gamestudio.service.*;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private CommentService commentService;

    public ConsoleUI(ScoreService scoreService, RatingService ratingService, CommentService commentService) {
        this.scoreService = scoreService;
        this.ratingService = ratingService;
        this.commentService = commentService;
    }

    public ConsoleUI() {
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    private String name;
    private int level;
    private long time;
    private int score;
    private int rating;
    private String comment;

    private int rows, columns;
    private int row, column;

    private static final Pattern COMMAND_LINE = Pattern.compile("([A-D])([1-9])");

    public void sayHello() {
        System.out.println("Welcome to the " + ANSI_YELLOW + "Lights off!" + ANSI_RESET);
        System.out.print("Before we start, write please your " + ANSI_BLUE + "name" + ANSI_RESET + ": ");
        name = scanner.nextLine();
        System.out.println("Nice to meet you, " + ANSI_BLUE + name + ANSI_RESET + "!");
        System.out.print("Which " + ANSI_BLUE + "level " + ANSI_RESET + "do you want to choose?(1,2,3) : ");

        while(true) {
            level = scanner.nextInt();
            if (level < 1 || level > 3) {
                System.out.print(ANSI_RED + "Enter one of the number in brackets" + ANSI_RESET + ": ");
            } else {
                convertLevel(level);
                break;
            }
        }
    }

    public void sayThroughGame() {
        System.out.print("Enter command " + ANSI_PURPLE + "(99 - to exit, a1 - to switch light) " + ANSI_RESET + ": ");
        String line = scanner.next().toUpperCase();
        while(true) {
            if(line.equals("99")) {
                row = 99;
                break;
            }
            Matcher matcher = COMMAND_LINE.matcher(line);
            if(matcher.matches()) {
                row = line.charAt(0) - 'A';
                column = Integer.parseInt(matcher.group(2)) - 1;
                if(column + 1 <= columns || line.equals("99")) {
                    break;
                } else {
                    System.out.print(ANSI_RED + "Enter valid command " + ANSI_RESET + ": ");
                    line = scanner.next().toUpperCase();
                }
            } else {
                System.out.print(ANSI_RED + "Enter valid command " + ANSI_RESET + ": ");
                line = scanner.next().toUpperCase();
            }
        }
    }

    public void sayGoodbye(ConsoleUI consoleUI, StatusGame status, long startTime, long endTime, int moves) {
        consoleUI.setTime(consoleUI.timeConverter(endTime - startTime) + 1);

        System.out.println();
        if(status == StatusGame.LOSE) {
            consoleUI.setScore(0);
            System.out.println(name + ", you are " + ANSI_YELLOW + "lose:(" + ANSI_RESET);
        }
        if(status == StatusGame.WIN) {
            consoleUI.setScore(100 - moves);
            System.out.println("Congratulations, " + name + "!" + " You are " + ANSI_YELLOW + "win!" + ANSI_RESET);
        }

        scoreService.addScore(new Score("lightsOff", consoleUI.getName(), consoleUI.getScore(), consoleUI.getTime(), consoleUI.getLevel(), new Date()));

        showTop();
        inputAndShowRate(consoleUI);
        scanner.nextLine();//for scanner's valid work
        inputAndShowComments(consoleUI);
    }

    private void showTop() {
        List<Score> playersTop = scoreService.getTopScores("lightsOff");
        System.out.println();
        System.out.println(ANSI_CYAN + "/////////////////////////////////////////////////////////////////" + ANSI_RESET);
        for(Score score : playersTop) {
            System.out.println("Player: " + score.getPlayer() + "; Points: "
                    + score.getPoints() + "; Played on: " + score.getPlayedAt()
                    + "; Minutes: " + score.getMinutesOfPlaying() + "; Level: " + score.getLevel());
        }
        System.out.print(ANSI_CYAN + "/////////////////////////////////////////////////////////////////" + ANSI_RESET);
        System.out.println();
        System.out.println();
    }

    private void inputAndShowRate(ConsoleUI consoleUI) {
        do {
            System.out.print("Enter the " + ANSI_BLUE + "rating " + ANSI_RESET + "for this game (from 1 to 5): ");
            String num = scanner.next();
            boolean validRating = true;
            switch (num) {
                case "1":
                    consoleUI.setRating(1);
                    break;
                case "2":
                    consoleUI.setRating(2);
                    break;
                case "3":
                    consoleUI.setRating(3);
                    break;
                case "4":
                    consoleUI.setRating(4);
                    break;
                case "5":
                    consoleUI.setRating(5);
                    break;
                default:
                    validRating = false;
                    break;
            }
            if(validRating) {
                break;
            }
        } while(true);
        ratingService.setRating(new Rating("lightsOff", consoleUI.getName(), consoleUI.getRating(), new Date()));
        System.out.println(ANSI_CYAN + "/////////////////////////////////////////////////////////////////" + ANSI_RESET);
        System.out.println(consoleUI.getName() + ", thank you for your rating - " + ANSI_GREEN + ratingService.getRating("lightsOff", consoleUI.getName()) + ANSI_RESET);
        System.out.println("The " + ANSI_BLUE + "average " + ANSI_RESET + "rating from all people is: " + ANSI_GREEN + ratingService.getAverageRating("lightsOff") + ANSI_RESET);
        System.out.print(ANSI_CYAN + "/////////////////////////////////////////////////////////////////" + ANSI_RESET);
        System.out.println();
        System.out.println();
    }

    private void inputAndShowComments(ConsoleUI consoleUI) {
        System.out.println("Enter the " + ANSI_BLUE + "comment " + ANSI_RESET + "for this game: ");
        consoleUI.setComment(scanner.nextLine());
        commentService.addComment(new Comment("lightsOff", consoleUI.getName(), consoleUI.getComment(), new Date()));
        List<Comment> commentList = commentService.getComments("lightsOff");
        System.out.println(ANSI_CYAN + "/////////////////////////////////////////////////////////////////" + ANSI_RESET);
        for(Comment comment : commentList) {
            System.out.println("Player: " + comment.getPlayer() + "; Comment: "
                    + comment.getComment() + "; Commented on: " + comment.getCommentedOn());
        }
        System.out.print(ANSI_CYAN + "/////////////////////////////////////////////////////////////////" + ANSI_RESET);
        System.out.println();
    }

    public void drawField(int[][] fieldArray, int columns) {
        char letter = 65;
        printNumbers(columns);
        System.out.println();
        for(int i = 0; i < 4; i++) { //draw column
            printLine(columns);
            System.out.println();
            for(int j = 0; j < (columns * 4) + 1; j++) { // j < 4*n+1, where n is count of columns; draw row
                if(j % 4 == 0) {
                    if(j == 0) {
                        System.out.print(letter + " ");
                        letter++;
                    }
                    System.out.print("|");
                } else if((j + 2) % 4 == 0) {
                    int posX = (j + 2) / 4 - 1;
                    if(fieldArray[i][posX] == 1) {
                        System.out.print("*");
                    } else {
                        System.out.print(" ");
                    }
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        printLine(columns);
        System.out.println();
    }

    private void printNumbers(int columns) {
        int num = 1;
        for(int j = 0; j < (columns * 4) + 1; j++) {
            if(j % 4 == 0 && j != 0) {
                System.out.print(num++);
            } else {
                System.out.print(" ");
            }
        }
    }

    private void printLine(int countOfColumns) {
        countOfColumns = (countOfColumns * 4) + 3;
        for(int j = 0; j < countOfColumns; j++) {
            if(j == 0 || j == 1) {
                System.out.print(" ");
            } else {
                System.out.print("-");
            }
        }
    }

    public void convertLevel(int level) {
        switch (level) {
            case 1:
                columns = 5;
                break;
            case 2:
                columns = 6;
                break;
            case 3:
                columns = 7;
        }
    }

    public long timeConverter(long time) {
        return (time / 60000);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
