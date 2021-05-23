package sk.tuke.gamestudio.game.lightsoff.core;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.game.lightsoff.consoleUI.ConsoleUI;

import java.util.Random;

public class Logic {

    private ConsoleUI consoleUI;

    @Autowired
    public Logic(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    public void randomFill(int[][] field, int columns) {
         Random rand = new Random();
         for(int i = 0; i < 4; i++) {
             for(int j = 0; j < columns; j++) {
                 field[i][j] = rand.nextInt(2);
             }
         }
     }

    public void switchStars(int[][] field, int y, int x, int countOfColumns) {
         if(field[y][x] == StateTile.ON.getValue()) {
             field[y][x] = StateTile.OFF.getValue();
         } else {
             field[y][x] = StateTile.ON.getValue();
         }
         if(x - 1 != -1) {
             if(field[y][x - 1] == StateTile.ON.getValue()) {
                 field[y][x - 1] = StateTile.OFF.getValue();
             } else {
                 field[y][x - 1] = StateTile.ON.getValue();
             }
         }
         if(x + 1 < countOfColumns) {
             if(field[y][x + 1] == StateTile.ON.getValue()) {
                 field[y][x + 1] = StateTile.OFF.getValue();
             } else {
                 field[y][x + 1] = StateTile.ON.getValue();
             }
         }
         if(y - 1 != -1) {
             if(field[y - 1][x] == StateTile.ON.getValue()) {
                 field[y - 1][x] = StateTile.OFF.getValue();
             } else {
                 field[y - 1][x] = StateTile.ON.getValue();
             }
         }
         if(y + 1 < 4) {
             if(field[y + 1][x] == StateTile.ON.getValue()) {
                 field[y + 1][x] = StateTile.OFF.getValue();
             } else {
                 field[y + 1][x] = StateTile.ON.getValue();
             }
         }
    }

    public boolean allLightsAreOff(int[][] field, ConsoleUI consoleUI) {
        boolean checker = true;
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < consoleUI.getColumns(); j++) {
                if (field[i][j] == StateTile.ON.getValue()) {
                    checker = false;
                }
            }
        }
        return checker;
    }

    public int[][] generateField() {
        consoleUI.setRows(4);
         int[][] field = new int[consoleUI.getRows()][consoleUI.getColumns()];
         randomFill(field, consoleUI.getColumns());
//        field[0][0] = StateTile.ON.getValue();
//        field[0][1] = StateTile.ON.getValue();
//        field[1][0] = StateTile.ON.getValue();
        return field;
    }

    public void play() {
        consoleUI.sayHello();
        StatusGame status = StatusGame.PLAYING;
        int[][] field = generateField();
        long startTime, endTime;
        int moves = 0;

        consoleUI.drawField(field, consoleUI.getColumns());

        startTime = System.currentTimeMillis();
        while(status == StatusGame.PLAYING) {
            consoleUI.sayThroughGame();
            if(consoleUI.getRow() == 99 || moves == 100) {
                status = StatusGame.LOSE;
                break;
            }
            switchStars(field, consoleUI.getRow(), consoleUI.getColumn(), consoleUI.getColumns());
            consoleUI.drawField(field, consoleUI.getColumns());

            boolean win = allLightsAreOff(field, consoleUI);
            if(win) {
                status = StatusGame.WIN;
                break;
            }
            moves++;
        }
        endTime = System.currentTimeMillis();

        consoleUI.sayGoodbye(consoleUI, status, startTime, endTime, moves);
    }
}