package com.davidread.lightsout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * {@link MainActivity} is an activity class that represents the user interface for the lights out
 * game. It maintains the logic of the game in {@link #game} and displays the logic of the game
 * using {@link #lightGrid}.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * {@link LightsOutGame} representing the logic of the current lights out game.
     */
    private LightsOutGame game;

    /**
     * {@link GridLayout} used for displaying the board of the lights out game.
     */
    private GridLayout lightGrid;

    /**
     * Int representation of the color used for a lit cell.
     */
    private int lightOnColor;

    /**
     * Int representation of the color used for a dark cell.
     */
    private int lightOffColor;

    /**
     * Callback method invoked when the activity is initially created. It simply initializes the
     * member variables of this class and makes an initial call to {@link #startGame()}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightGrid = findViewById(R.id.light_grid);

        lightOnColor = ContextCompat.getColor(this, R.color.yellow);
        lightOffColor = ContextCompat.getColor(this, R.color.black);

        game = new LightsOutGame();
        startGame();
    }

    /**
     * Initializes {@link #game} with a new game state. Then, it updates {@link #lightGrid} to
     * match {@link #game}.
     */
    private void startGame() {
        game.newGame();
        setButtonColors();
    }

    /**
     * Method invoked when a light button is clicked. It registers the click with {@link #game} and
     * updates {@link #lightGrid} to match it. Then, it pops a {@link Snackbar} if the game is in
     * a terminal state.
     *
     * @param view {@link View} clicked that invoked this method.
     */
    public void onLightButtonClick(View view) {

        // Find the button's row and col.
        int buttonIndex = lightGrid.indexOfChild(view);
        int row = buttonIndex / LightsOutGame.GRID_SIZE;
        int col = buttonIndex % LightsOutGame.GRID_SIZE;

        game.selectLight(row, col);
        setButtonColors();

        // Congratulate the user if the game.
        if (game.isGameOver()) {
            Snackbar.make(view, R.string.game_win_message, BaseTransientBottomBar.LENGTH_SHORT).show();
        }
    }

    /**
     * Updates {@link #lightGrid} to match the state given by {@link #game}.
     */
    private void setButtonColors() {

        // Set all buttons' background color.
        for (int row = 0; row < LightsOutGame.GRID_SIZE; row++) {
            for (int col = 0; col < LightsOutGame.GRID_SIZE; col++) {

                // Find the button in the grid layout at this row and col.
                int buttonIndex = row * LightsOutGame.GRID_SIZE + col;
                Button gridButton = (Button) lightGrid.getChildAt(buttonIndex);

                if (game.isLightOn(row, col)) {
                    gridButton.setBackgroundColor(lightOnColor);
                } else {
                    gridButton.setBackgroundColor(lightOffColor);
                }
            }
        }
    }

    /**
     * Method invoked when the new game button is clicked. It just calls {@link #startGame()}.
     *
     * @param view {@link View} clicked that invoked this method.
     */
    public void onNewGameClick(View view) {
        startGame();
    }
}