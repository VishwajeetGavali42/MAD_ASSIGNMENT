package com.example.dairymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "DairyDB";
    private static final int DB_VERSION = 5; // ← UPDATED

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS TABLE
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT)");

        // MEMBERS TABLE
        db.execSQL("CREATE TABLE IF NOT EXISTS members (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT UNIQUE)");

        // MILK RECORDS TABLE
        db.execSQL("CREATE TABLE IF NOT EXISTS milk_records (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "litres REAL," +
                "fat REAL," +
                "amount REAL," +
                "date TEXT," +
                "type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS members");
        db.execSQL("DROP TABLE IF EXISTS milk_records");
        onCreate(db);
    }

    // REGISTER USER
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

    // CHECK LOGIN
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM users WHERE username=? AND password=?",
                new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // ADD MEMBER
    public boolean addMember(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        long result = db.insert("members", null, values);
        db.close();
        return result != -1;
    }

    // GET ALL MEMBERS
    public ArrayList<String> getAllMemberNames() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM members ORDER BY name ASC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(cursor.getString(0));
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    // INSERT MILK RECORD
    public void insertRecord(String name, double litres, double fat,
                             double amount, String date, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("litres", litres);
        values.put("fat", fat);
        values.put("amount", amount);
        values.put("date", date);
        values.put("type", type);
        db.insert("milk_records", null, values);
        db.close();
    }

    // GET ALL RECORDS
    public ArrayList<MilkRecord> getAllRecords() {
        ArrayList<MilkRecord> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM milk_records ORDER BY id DESC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MilkRecord m = new MilkRecord();
                m.id = cursor.getInt(0);
                m.name = cursor.getString(1);
                m.litres = cursor.getDouble(2);
                m.fat = cursor.getDouble(3);
                m.amount = cursor.getDouble(4);
                m.date = cursor.getString(5);
                m.type = cursor.getString(6);
                list.add(m);
            }
            cursor.close();
        }
        db.close();
        return list;
    }

    // UPDATE RECORD
    public void updateRecord(int id, String name, double litres, double fat, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("litres", litres);
        values.put("fat", fat);
        values.put("amount", amount);
        db.update("milk_records", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // DELETE RECORD
    public void deleteRecord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("milk_records", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}