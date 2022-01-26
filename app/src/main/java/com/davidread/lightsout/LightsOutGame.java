package com.davidread.lightsout;

import java.util.Random;

/**
 * {@link LightsOutGame} is a model class for the lights out game. The game involves a grid of
 * randomly initialized light/dark cells. When a cell is selected, itself and adjacent cells are
 * inverted. The goal of the game is to darken all cells in the grid.
 */
public class LightsOutGame {

    /**
     * Int constant for board grid size.
     */
    public static final int GRID_SIZE = 3;

    /**
     * 2D boolean array representing the board. True cells are lit up, false cells are dark.
     */
    private final boolean[][] lightsGrid;

    /**
     * Constructs a new {@link LightsOutGame}.
     */
    public LightsOutGame() {
        lightsGrid = new boolean[GRID_SIZE][GRID_SIZE];
    }

    /**
     * Restarts the game with a new randomly generated {@link #lightsGrid}.
     */
    public void newGame() {
        Random randomNumGenerator = new Random();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                lightsGrid[row][col] = randomNumGenerator.nextBoolean();
            }
        }
    }

    /**
     * Returns true if the {@link #lightsGrid} cell at the given coordinates is lit.
     *
     * @param row Row coordinate.
     * @param col Column coordinate.
     */
    public boolean isLightOn(int row, int col) {
        return lightsGrid[row][col];
    }

    /**
     * Inverts the {@link #lightsGrid} cell at the given coordinates and all adjacent cells.
     *
     * @param row Row coordinate.
     * @param col Column coordinate.
     */
    public void selectLight(int row, int col) {
        lightsGrid[row][col] = !lightsGrid[row][col];
        if (row > 0) {
            lightsGrid[row - 1][col] = !lightsGrid[row - 1][col];
        }
        if (row < GRID_SIZE - 1) {
            lightsGrid[row + 1][col] = !lightsGrid[row + 1][col];
        }
        if (col > 0) {
            lightsGrid[row][col - 1] = !lightsGrid[row][col - 1];
        }
        if (col < GRID_SIZE - 1) {
            lightsGrid[row][col + 1] = !lightsGrid[row][col + 1];
        }
    }

    /**
     * Returns true if the game is over. This happens when all the lights are off.
     */
    public boolean isGameOver() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (lightsGrid[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }
}
