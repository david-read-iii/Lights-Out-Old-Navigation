package com.davidread.lightsoutoldnavigation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * {@link MainActivity} is an activity class that represents the user interface for the lights out
 * game. It maintains the logic of the game in {@link #game} and displays the logic of the game
 * using {@link #lightGrid}.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * {@link String} constant for identifying the game state passed in a {@link Bundle} during
     * configuration changes.
     */
    private static final String GAME_STATE_EXTRA = "game_state";

    /**
     * {@link ActivityResultLauncher} for setting {@link #lightOnColorId} and {@link #lightOnColor}
     * in response to {@link ColorActivity} finishes.
     */
    private final ActivityResultLauncher<Intent> colorResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            lightOnColorId = data.getIntExtra(ColorActivity.COLOR_EXTRA, R.color.yellow);
                            lightOnColor = ContextCompat.getColor(MainActivity.this, lightOnColorId);
                            setButtonColors();
                        }
                    }
                }
            }
    );

    /**
     * {@link LightsOutGame} representing the logic of the current lights out game.
     */
    private LightsOutGame game;

    /**
     * {@link GridLayout} used for displaying the board of the lights out game.
     */
    private GridLayout lightGrid;

    /**
     * {@link TextView} used for displaying the count of clicks made in the game so far.
     */
    private TextView countTextView;

    /**
     * Int color ID used for a lit cell.
     */
    private int lightOnColorId;

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
     * member variables of this class and makes an initial call to {@link #startGame()} and
     * {@link #setCountTextView()}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize member variables.
        game = new LightsOutGame();
        lightGrid = findViewById(R.id.light_grid);
        countTextView = findViewById(R.id.count_text_view);
        lightOnColorId = R.color.yellow;
        lightOnColor = ContextCompat.getColor(this, R.color.yellow);
        lightOffColor = ContextCompat.getColor(this, R.color.black);

        // Start the game and update the count TextView.
        startGame();
        setButtonColors();
        setCountTextView();
    }

    /**
     * Callback method invoked directly before a configuration change occurs. It saves the state of
     * {@link #game}.
     *
     * @param outState {@link Bundle} for preserving state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE_EXTRA, game.getState());
    }

    /**
     * Callback method invoked after a configuration change. It restores the state of {@link #game}.
     *
     * @param savedInstanceState {@link Bundle} holding state from before the configuration changed.
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        game.setState(savedInstanceState.getString(GAME_STATE_EXTRA));
        setButtonColors();
        setCountTextView();
    }

    /**
     * Initializes {@link #game} with a new game state. Then, it updates {@link #lightGrid} to
     * match {@link #game}.
     */
    private void startGame() {
        game.newGame();
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
     * Updates {@link #countTextView} with the latest count from {@link #game}.
     */
    public void setCountTextView() {
        countTextView.setText(getString(R.string.count_label, game.getCountClicks()));
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

        // Select light in game.
        game.selectLight(row, col);

        // Update game UI.
        setButtonColors();
        setCountTextView();

        // Congratulate the user and start a new game if the game is over.
        if (game.isGameOver()) {
            Snackbar.make(view, getString(R.string.game_win_message, game.getCountClicks()), BaseTransientBottomBar.LENGTH_SHORT).show();
            startGame();
            setButtonColors();
            setCountTextView();
        }
    }

    /**
     * Method invoked when the new game button is clicked. It just calls {@link #startGame()}.
     *
     * @param view {@link View} clicked that invoked this method.
     */
    public void onNewGameClick(View view) {
        startGame();
        setButtonColors();
        setCountTextView();
    }

    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    public void onChangeColorClick(View view) {
        Intent intent = new Intent(this, ColorActivity.class);
        intent.putExtra(ColorActivity.COLOR_EXTRA, lightOnColorId);
        colorResultLauncher.launch(intent);
    }
}