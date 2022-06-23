package com.example.demo.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonSQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Persons.db";
    public static final int VERSION = 1;

    public PersonSQLHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PersonDAO.SQL_CREATE_TABLE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }




}
