package com.example.vikramjeet.simpletodo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vikramjeet on 1/14/15.
 */
public class Item implements Serializable {
    public String itemText;
    public String itemDueDate;

    public Item(String text, Date tomorrowDate) {
        // Set item text
        this.itemText = text;

        //Set date formatter and itemDueDate
        DateFormat dateFormat = SimpleDateFormat.getDateInstance();
        this.itemDueDate = "Due on : " + dateFormat.format(tomorrowDate);
    }
}
