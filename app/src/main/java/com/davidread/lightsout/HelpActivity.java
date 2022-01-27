package com.davidread.lightsout;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * {@link HelpActivity} represents a user interface with instructions that tell the user how to play
 * the Lights Out game.
 */
public class HelpActivity extends AppCompatActivity {

    /**
     * Callback method invoked when the activity is created. It simply inflates the layout of the
     * activity.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
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
}
