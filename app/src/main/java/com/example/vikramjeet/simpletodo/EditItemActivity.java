package com.example.vikramjeet.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

    private EditText editItem;
    private int indexToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editItem = (EditText) findViewById(R.id.editItem);

        // Retrieve item text from Intent's extra property
        String itemText = this.getIntent().getStringExtra("itemToEdit");
        indexToEdit = this.getIntent().getIntExtra("indexToEdit", -1);

        // Set Selected item
        editItem.setText(itemText);

        // Place cursor at the end of the text
        editItem.setSelection(editItem.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Button callback methods

    public void didClickSaveButton(View v) {
        // Get new item
        String newItem = editItem.getText().toString();

        // Prepare data intent
        Intent data = new Intent();

        // Pass relevant data back as result
        data.putExtra("newItem", newItem);
        data.putExtra("indexToEdit", indexToEdit);

        // Set Result code and bundle data
        setResult(RESULT_OK, data);

        // Closes the activity and pass data to parent
        finish();
    }
}
