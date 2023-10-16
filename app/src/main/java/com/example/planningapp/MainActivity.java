package com.example.planningapp;

import static com.example.planningapp.CalendarUtils.daysInMonthArray;
import static com.example.planningapp.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalenderAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
       // loadFromDBToMemory();
        setMonthView();
    }

    private void loadFromDBToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateEventListArray();
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalenderAdapter calenderAdapter = new CalenderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calenderAdapter);

    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }
    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate= CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate= CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }


    public void onItemClick(int position, LocalDate date) {
        if (date !=null){
        CalendarUtils.selectedDate = date;
        setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
}