package sk.tuke.gamestudio.lightsoff.core;

import sk.tuke.gamestudio.game.lightsoff.consoleUI.ConsoleUI;
import sk.tuke.gamestudio.game.lightsoff.core.Logic;
import sk.tuke.gamestudio.game.lightsoff.core.StateTile;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogicTest {
    private ConsoleUI consoleUI = new ConsoleUI();

    private Logic create() {
        return new Logic(consoleUI);
    }

    private boolean allLightsAreOff(int[][] field) {
        boolean checker = true;
        for(int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (field[i][j] == StateTile.ON.getValue()) {
                    checker = false;
                }
            }
        }
        return checker;
    }

    @Test
    public void testAllLightsAreOffTrue() {
        Logic logic = create();

        int[][] field = new int[4][5];
        assertTrue(allLightsAreOff(field));
    }

    @Test
    public void testAllLightsAreOffFlase() {
        Logic logic = create();

        int[][] field = new int[4][5];
        field[2][2] = StateTile.ON.getValue();
        field[2][1] = StateTile.ON.getValue();
        field[2][3] = StateTile.ON.getValue();

        assertFalse(allLightsAreOff(field));
    }

    @Test
    public void testSwitchStarsCenter() {
        Logic logic = create();

        int[][] field = new int[4][5];
        field[2][2] = StateTile.ON.getValue();
        field[2][1] = StateTile.ON.getValue();
        field[2][3] = StateTile.ON.getValue();
        field[1][2] = StateTile.ON.getValue();
        field[3][2] = StateTile.ON.getValue();
        logic.switchStars(field, 2, 2, 5);

        assertTrue(allLightsAreOff(field));
    }

    @Test
    public void testSwitchStarsBorder() {
        Logic logic = create();

        int[][] field = new int[4][5];
        field[1][0] = StateTile.ON.getValue();
        field[0][0] = StateTile.ON.getValue();
        field[2][0] = StateTile.ON.getValue();
        field[1][1] = StateTile.ON.getValue();
        logic.switchStars(field, 1, 0, 5);

        assertTrue(allLightsAreOff(field));
    }

    @Test
    public void testSwitchStarsAngle() {
        Logic logic = create();

        int[][] field = new int[4][5];
        field[0][4] = StateTile.ON.getValue();
        field[0][3] = StateTile.ON.getValue();
        field[1][4] = StateTile.ON.getValue();
        logic.switchStars(field, 0, 4, 5);

        assertTrue(allLightsAreOff(field));
    }
}
