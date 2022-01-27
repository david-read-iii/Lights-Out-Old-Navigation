package com.davidread.lightsout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.button.MaterialButton;

/**
 * {@link ColorActivity} represents a user interface with {@link MaterialButton} views that allow
 * the user to change the color of the squares of the Lights Out game.
 */
public class ColorActivity extends AppCompatActivity {

    /**
     * {@link String} constant used to identify the color ID in a {@link Bundle} passed between the
     * {@link MainActivity} and {@link ColorActivity}.
     */
    public static final String COLOR_EXTRA = "color";

    /**
     * Callback method invoked when the activity is created. First, it inflates the layout of this
     * activity. Then, it receives the color ID used to light squares up from {@link MainActivity}.
     * Finally, it puts the {@link MaterialButton} in the layout that represents the color ID in a
     * selected state.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        // Get the color ID from MainActivity.
        Intent intent = getIntent();
        int colorId = intent.getIntExtra(COLOR_EXTRA, R.color.yellow);

        // Select the ID of the MaterialButton matching the color ID.
        int buttonId = R.id.yellow_button;
        if (colorId == R.color.red) {
            buttonId = R.id.red_button;
        } else if (colorId == R.color.orange) {
            buttonId = R.id.orange_button;
        } else if (colorId == R.color.green) {
            buttonId = R.id.green_button;
        }

        // Put the selected MaterialButton in a selected state.
        MaterialButton selectedButton = findViewById(buttonId);
        selectedButton.setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_radio_button_checked));
    }

    /**
     * Callback method invoked when action bar items are selected. It simply specifies what to do
     * when the up action bar item is selected.
     *
     * @param item Selected {@link MenuItem}.
     * @return False to allow normal menu processing to proceed. True to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Have up button mimic back button behavior. Specifically for the back button animation.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method invoked when a {@link MaterialButton} is selected in this activity.
     *
     * @param view {@link View} selected.
     */
    public void onColorSelected(View view) {

        // Get a color ID based on which MaterialButton was selected.
        int colorId = R.color.yellow;
        if (view.getId() == R.id.red_button) {
            colorId = R.color.red;
        } else if (view.getId() == R.id.orange_button) {
            colorId = R.color.orange;
        } else if (view.getId() == R.id.green_button) {
            colorId = R.color.green;
        }

        // Finish this activity and pass the color ID to MainActivity.
        Intent intent = new Intent();
        intent.putExtra(COLOR_EXTRA, colorId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
