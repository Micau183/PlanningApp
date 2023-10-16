package com.example.planningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {


    private EditText eventNameET, eventDateET, eventTimeET;
    private LocalTime time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateET = findViewById(R.id.eventDateET);
        eventTimeET = findViewById(R.id.eventTimeET);
    }

    public void saveEventAction(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String eventName = eventNameET.getText().toString();
        String eventDate = eventDateET.getText().toString();
        String eventTime = eventTimeET.getText().toString();
        LocalDate date = CalendarUtils.toDate(eventDate);
        LocalTime time = CalendarUtils.toTime(eventTime);
        int id = Event.eventsList.size();

        Event newEvent = new Event(id, eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        sqLiteManager.addEventToDatabase(newEvent);
        finish();
    }
}