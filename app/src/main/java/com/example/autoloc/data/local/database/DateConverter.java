package com.example.autoloc.data.local.database;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class DateConverter {

    @TypeConverter
    public static String fromLocalDate(LocalDate date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static LocalDate toLocalDate(String date) {
        return date == null ? null : LocalDate.parse(date);
    }
}
