package com.example.ojboba.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ojboba.habittracker.Data.HabitContract;
import com.example.ojboba.habittracker.Data.HabitDbHelper;

/**
 * Created by OjBoba on 2/6/2017.
 */

public class AddHabit extends AppCompatActivity {
    /** EditText field to enter the habit name */
    private EditText mNameEditText;

    /** EditText field to enter the time */
    private EditText mTimeEditText;

    /** EditText field to enter the difficulty */
    private Spinner mDifficultySpinner;


    private int mDifficulty = HabitContract.HabitEntry.DIFFICULTY_EASY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mTimeEditText = (EditText) findViewById(R.id.edit_habit_time);
        mDifficultySpinner = (Spinner) findViewById(R.id.spinner_difficulty);
        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter difficultySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_difficulty_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        difficultySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mDifficultySpinner.setAdapter(difficultySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mDifficultySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.difficulty_Hard))) {
                        mDifficulty = HabitContract.HabitEntry.DIFFICULTY_HARD;
                    } else if (selection.equals(getString(R.string.difficulty_Medium))) {
                        mDifficulty = HabitContract.HabitEntry.DIFFICULTY_MEDIUM;
                    } else {
                        mDifficulty = HabitContract.HabitEntry.DIFFICULTY_EASY;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mDifficulty = HabitContract.HabitEntry.DIFFICULTY_EASY;
            }
        });
    }

    /**
     * Get user input from editor and save new pet into database.
     */
    private void insertHabit() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameHabit = mNameEditText.getText().toString().trim();
        String timeString = mTimeEditText.getText().toString().trim();


        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, nameHabit);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_TIME, timeString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DIFFICULTY, mDifficulty);


        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertHabit();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

