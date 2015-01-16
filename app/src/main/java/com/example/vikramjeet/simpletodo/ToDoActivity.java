package com.example.vikramjeet.simpletodo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ToDoActivity extends ActionBarActivity implements EditItemFragment.EditItemFragmentListener {

    private ArrayList<Item> items;
    private ToDoAdapter itemsAdapter;
    private ListView lvItems;
    private EditText newItemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        // Read data from the file
        readItems(getApplicationContext());

        newItemText = (EditText) findViewById(R.id.newItemText);
        lvItems = (ListView) findViewById(R.id.lvItems);

        itemsAdapter = new ToDoAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener(lvItems);
    }

    private void showDialogView(String itemToEdit, int index) {
        // get FragmentManager
        FragmentManager manager = getSupportFragmentManager();

        // get fragment
        EditItemFragment fragment = EditItemFragment.newInstance(itemToEdit, index);
        fragment.show(manager, "fragment_edit_item");
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

    public void updateItem(String newItem, int index) {

        // get Calendar object
        Calendar calendar = Calendar.getInstance();

        // get today's date and add 1 to it for tomorrow's date
        Date today = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();

        // Create an item object
        Item updatedItem = new Item(newItem, tomorrow);

        // Update ArrayAdapter with new value
        itemsAdapter.insert(updatedItem, index);

        // Remove previous entry and update UI
        items.remove(++index);
        itemsAdapter.notifyDataSetChanged();

        // Save data to the file
        writeItems(getApplicationContext());
    }

    // Button callback methods

    public void didClickAddItem(View v) {
        // Retrieve newItem textfield view from resources
        String newItemStr = newItemText.getText().toString();

        // get Calendar object
        Calendar calendar = Calendar.getInstance();

        // get today's date and add 1 to it for tomorrow's date
        Date today = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();

        // Create an item object
        Item newItem = new Item(newItemStr, tomorrow);

        // Add new value to ArrayAdapter
        itemsAdapter.add(newItem);

//      Why not below line?
//      items.add(newItemStr);
        newItemText.setText("");

        // Save data to the file
        writeItems(getApplicationContext());
    }

    // ListView Listener methods

    private void setupListViewListener(ListView lv) {
        lv.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                    items.remove(pos);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems(getApplicationContext());
                    return true;
                }
            });

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        // Create DialogFragment to edit an item
                        Item itemToEdit = items.get(pos);
                        String itemText = itemToEdit.itemText;
                        showDialogView(itemText, pos);
                    }
                }
        );
    }


    public void onFinishEditDialog(String itemToEdit, int indexToUpdate) {
        this.updateItem(itemToEdit, indexToUpdate);
    }

    // Read method

    private void readItems(Context context) {
        FileInputStream fis;
        try {
            fis = context.openFileInput("tod.txt");
            ObjectInputStream oi = new ObjectInputStream(fis);
            items = (ArrayList) oi.readObject();
            oi.close();
        } catch (FileNotFoundException e) {
            Log.e("InternalStorage", e.getMessage());
        } catch (IOException e) {
            Log.e("InternalStorage", e.getMessage());
        }
        catch (ClassNotFoundException e) {
            Log.e("InternalStorage", e.getMessage());
        }
    }

    // Write method

    private void writeItems(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("tod.txt", Context.MODE_PRIVATE);
            ObjectOutputStream of = new ObjectOutputStream(fos);
            of.writeObject(items);
            of.flush();
            of.close();
            fos.close();
        }
        catch (Exception e) {
            Log.e("InternalStorage", e.getMessage());
        }

    }
}
