package com.example.planningapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


public class SQLiteManager extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "NoteDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Note";
    private static final String COUNTER = "Counter";
    private static final String ID_FIELD = "id";
    private static final String EVENT_NAME_FIELD = "EventName";
    private static final String EVENT_DATE_FIELD = "EventDate";
    private static final String EVENT_TIME_FIELD = "EventTime";

    private static final String DELETED_FIELD = "deleted";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(EVENT_NAME_FIELD)
                .append(" TEXT, ")
                .append(EVENT_DATE_FIELD)
                .append(" TEXT, ")
                .append(EVENT_TIME_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
//        switch (oldVersion)
//        {
//            case 1:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }
    }

    public void addEventToDatabase(Event event)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, event.getId());
        contentValues.put(EVENT_NAME_FIELD, event.getName());
        contentValues.put(EVENT_DATE_FIELD, CalendarUtils.formattedDate(event.getDate()));
        contentValues.put(EVENT_NAME_FIELD, CalendarUtils.formattedTime(event.getTime()));


        //contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateEventListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String StrDate = result.getString(3);
                    String StrTime = result.getString(4);
                    LocalDate date = CalendarUtils.toDate(StrDate);
                    LocalTime time = CalendarUtils.toTime(StrTime);

                    //String stringDeleted = result.getString(4);
                    //Date deleted = getDateFromString(stringDeleted);
                    Event event = new Event(id ,name, date, time);
                    Event.eventsList.add(event);
                }
            }
        }
    }

    public void updateEventInDB(Event event)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, event.getId());
        contentValues.put(EVENT_NAME_FIELD, event.getName());
        contentValues.put(EVENT_DATE_FIELD, CalendarUtils.formattedDate(event.getDate()));
        contentValues.put(EVENT_TIME_FIELD, CalendarUtils.formattedTime(event.getTime()));
       // contentValues.put(DELETED_FIELD, getStringFromDate(event.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(event.getId())});
    }

    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }
}
