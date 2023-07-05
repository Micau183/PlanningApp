package com.example.planningapp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CalendarUtils {
    public static LocalDate selectedDate;
    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);

    }
    public static LocalDate toDate(String strDate){
        System.out.println("ici " +strDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;

        try {
            date = LocalDate.parse(strDate, formatter);
        } catch (DateTimeParseException e) {
            // Gestion de l'exception en cas de format de date incorrect
            e.printStackTrace();
            date = selectedDate;
        }
        System.out.println(date);

        return date;

    }

    public static LocalTime toTime(String strTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time;
        try {
            time = LocalTime.parse(strTime, formatter);
        } catch (DateTimeParseException e) {
            // Gestion de l'exception en cas de format de date incorrect
            e.printStackTrace();
            time = LocalTime.now();
        }
        System.out.println(time);
        return time;
    }

    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        return time.format(formatter);

    }
    public static String formattedShortTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);

    }
    public static ArrayList<LocalDate> daysInMonthArray() {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();

        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate prevMonth = selectedDate.minusMonths(1);
        LocalDate nextMonth = selectedDate.plusMonths(1);

        YearMonth prevYearMonth = YearMonth.from(prevMonth);
        int prevDaysInMonth = prevYearMonth.lengthOfMonth();

        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);

        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++){
            if(i<= dayOfWeek){
                daysInMonthArray.add(LocalDate.of(prevMonth.getYear(),prevMonth.getMonth(), prevDaysInMonth + i-dayOfWeek));
            }
            else if (i > daysInMonth +dayOfWeek)
                daysInMonthArray.add(LocalDate.of(nextMonth.getYear(),nextMonth.getMonth(), i-dayOfWeek-daysInMonth));
            else{
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(), i-dayOfWeek));
            }
        }
        return daysInMonthArray;
    }
    public static String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String monthDayFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d");
        return date.format(formatter);
    }
    public static ArrayList<LocalDate> daysInWeeksArray(LocalDate selectedDate) {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = mondayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate)){
            days.add(current);
            current= current.plusDays(1);

        }
        return days;
    }

    private static LocalDate mondayForDate(LocalDate current) {
        LocalDate oneWeekAgo = current.minusWeeks(1);
        while (current.isAfter(oneWeekAgo)){
            if (current.getDayOfWeek() == DayOfWeek.MONDAY)
                return current;
            current = current.minusDays(1);
        }
        return null;
    }



}
