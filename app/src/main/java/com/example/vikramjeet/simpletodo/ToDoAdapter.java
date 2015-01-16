package com.example.vikramjeet.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vikramjeet on 1/14/15.
 */
public class ToDoAdapter extends ArrayAdapter {
    // View lookup cache
    private static class ViewHolder {
        TextView itemText;
        TextView itemDueDate;
    }

    public ToDoAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.item_todo, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item = (Item) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_todo, parent, false);
            viewHolder.itemText = (TextView) convertView.findViewById(R.id.itemText);
            viewHolder.itemDueDate = (TextView) convertView.findViewById(R.id.itemDueDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.itemText.setText(item.itemText);
        viewHolder.itemDueDate.setText(item.itemDueDate);
        // Return the completed view to render on screen
        return convertView;
    }
}
