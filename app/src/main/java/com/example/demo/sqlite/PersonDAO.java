package com.example.demo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.demo.cp.MyContentProvider;
import android.content.ContentResolver;

import java.util.ArrayList;
import java.util.List;

public class PersonDAO {
    private static final String TAG = PersonDAO.class.getSimpleName();

    public static final String TABLE_NAME = "person";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_AGE = "age";

    private static final String where = "id=?";

    public static final String SQL_CREATE_TABLE_PERSON =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_PHONE + " TEXT," +
                    COLUMN_AGE + " TEXT" +
                    ")";

    private final SQLiteDatabase db;
    private ContentResolver myCR;

    public PersonDAO(Context context) {
        PersonSQLHelper sqLiteHelper = new PersonSQLHelper(context);
        db = sqLiteHelper.getWritableDatabase();
        myCR = context.getContentResolver();
    }

    //CRUD SQLite
    public long insertPersonDAO(Person person) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, person.getName());
        values.put(COLUMN_PHONE, person.getPhone());
        values.put(COLUMN_AGE, person.getAge());
        return db.insert(TABLE_NAME, null, values);
    }

    public List<Person> getAllPersonDAO() {
        List<Person> list = new ArrayList<>();
        Cursor cursor =  db.query(TABLE_NAME,null,null,null,null,
                null,null);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
        int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
        int ageIndex = cursor.getColumnIndex(COLUMN_AGE);

        try {
            do {
                Person person = new Person();
                person.setId(cursor.getInt(idIndex));
                person.setName(cursor.getString(nameIndex));
                person.setPhone(cursor.getString(phoneIndex));
                person.setAge(cursor.getString(ageIndex));
                list.add(person);
                Log.d(TAG, String.format("Person: name: %d", person.getId()));
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e(TAG, "getAllPerson: ", e);
        } finally {
            cursor.close();
        }

        for (int i = 0; i <= list.size() - 1; i++) {
            Log.d(TAG, list.get(i).getName());
        }

        return list;
    }

    public int deleteEntryDAO(int id) {
        int numberOFEntriesDeleted = db.delete("Person", where,new String[]{String.valueOf(id)});
        return numberOFEntriesDeleted;
    }
    public int updateDAO(Person person){
        ContentValues values = new ContentValues();
        values.put("name",person.getName());
        values.put("phone",person.getPhone());
        values.put("age",person.getAge());
        return db.update(TABLE_NAME,values,where,new String[]{String.valueOf(person.getId())});

    }

    //CRUD CONTENT PROVIDER
    public void insertPersonCP(Person person) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, person.getName());
        values.put(COLUMN_PHONE, person.getPhone());
        values.put(COLUMN_AGE, person.getAge());
        myCR.insert(MyContentProvider.CONTENT_URI,values);
    }

    public List<Person> getAllPersonCP() {

        List<Person> list = new ArrayList<>();
        Cursor cursor =  db.query(TABLE_NAME,null,null,null,null,
                null,null);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex(COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
        int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
        int ageIndex = cursor.getColumnIndex(COLUMN_AGE);

        try {
            do {
                Person person = new Person();
                person.setId(cursor.getInt(idIndex));
                person.setName(cursor.getString(nameIndex));
                person.setPhone(cursor.getString(phoneIndex));
                person.setAge(cursor.getString(ageIndex));
                list.add(person);
                Log.d(TAG, String.format("Person: name: %d", person.getId()));
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e(TAG, "getAllPerson: ", e);
        } finally {
            cursor.close();
        }

        for (int i = 0; i <= list.size() - 1; i++) {
            Log.d(TAG, list.get(i).getName());
        }

        return list;
    }

    public int deleteEntryCP(String name) {
        int numberOFEntriesDeleted = db.delete("Person", where,new String[]{name});
        return numberOFEntriesDeleted;
    }
}
