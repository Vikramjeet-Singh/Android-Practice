package com.example.vikramjeet.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ToDoActivity extends ActionBarActivity {

    private final int REQUEST_CODE = 0;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener(lvItems);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract newItem value and index from result extras
            String newItem = data.getExtras().getString("newItem");
            int index = data.getExtras().getInt("indexToEdit");

            // Update ArrayAdapter with new value
            itemsAdapter.insert(newItem, index);

            // Remove previous entry and update UI
            items.remove(++index);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    // Button callback methods

    public void didClickAddItem(View v) {
        // Retrieve newItem textfield view from resources
        EditText newItem = (EditText) findViewById(R.id.newItemText);
        String newItemStr = newItem.getText().toString();
        itemsAdapter.add(newItemStr);
//        Why not below line?
//        items.add(newItemStr);
        newItem.setText("");
        writeItems();
    }

    // ListView Listener methods

    private void setupListViewListener(ListView lv) {
        lv.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                    items.remove(pos);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
                    return true;
                }
            });

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        // Create intent to launch Edit Activity
                        Intent editIntent = new Intent(ToDoActivity.this, EditItemActivity.class);
                        // Pass row data to intent
                        editIntent.putExtra("itemToEdit", ((TextView) item).getText().toString());
                        editIntent.putExtra("indexToEdit", pos);
                        startActivityForResult(editIntent, REQUEST_CODE);
                    }
                }
        );
    }

    // Read method

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            new ArrayList<String>();
        }
    }

    // Write method

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
