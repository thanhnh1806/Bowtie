package com.bowtie.Service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bowtie.Object.UserObject;

import java.util.ArrayList;

/**
 * Created by thanh_nguyen02 on 28/01/2016.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Bowtie.db";
    private static final int DATABASE_VERSION = 1;
    public static final String USER_TABLE_NAME = "User";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_IMAGE = "image";
    public static final String USER_COLUMN_FIRST_NAME = "firstName";
    public static final String USER_COLUMN_LAST_NAME = "lastName";
    public static final String USER_COLUMN_POSITION = "position";
    public static final String USER_COLUMN_PHONE_NUMBER_1 = "phoneNumber1";
    public static final String USER_COLUMN_PHONE_NUMBER_2 = "phoneNumber2";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_WEBSITE = "website";
    public static final String USER_COLUMN_LOCATION = "location";
    public static final String USER_COLUMN_LINKED = "linked";
    public static final String USER_COLUMN_TIME_UPDATE = "timeUpdate";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + "("
                + USER_COLUMN_ID + " TEXT PRIMARY KEY, "
                + USER_COLUMN_IMAGE + " TEXT, "
                + USER_COLUMN_FIRST_NAME + " TEXT, "
                + USER_COLUMN_LAST_NAME + " TEXT, "
                + USER_COLUMN_POSITION + " TEXT, "
                + USER_COLUMN_PHONE_NUMBER_1 + " TEXT, "
                + USER_COLUMN_PHONE_NUMBER_2 + " TEXT, "
                + USER_COLUMN_EMAIL + " TEXT, "
                + USER_COLUMN_WEBSITE + " TEXT, "
                + USER_COLUMN_LOCATION + " TEXT, "
               // + USER_COLUMN_LINKED + " TEXT, "
                + USER_COLUMN_LINKED + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<UserObject> getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<UserObject> list = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME, null);
        while (c.moveToNext()) {
            UserObject user = new UserObject();
            user.setId(c.getString(c.getColumnIndex(USER_COLUMN_ID)));
            user.setImage(c.getString(c.getColumnIndex(USER_COLUMN_IMAGE)));
            user.setFirstName(c.getString(c.getColumnIndex(USER_COLUMN_FIRST_NAME)));
            user.setLastName(c.getString(c.getColumnIndex(USER_COLUMN_LAST_NAME)));
            user.setPosition(c.getString(c.getColumnIndex(USER_COLUMN_POSITION)));
            user.setPhoneNumber1(c.getString(c.getColumnIndex(USER_COLUMN_PHONE_NUMBER_1)));
            user.setPhoneNumber2(c.getString(c.getColumnIndex(USER_COLUMN_PHONE_NUMBER_2)));
            user.setEmail(c.getString(c.getColumnIndex(USER_COLUMN_EMAIL)));
            user.setWebsite(c.getString(c.getColumnIndex(USER_COLUMN_WEBSITE)));
            user.setLocation(c.getString(c.getColumnIndex(USER_COLUMN_LOCATION)));
            user.setLinkedIn(c.getString(c.getColumnIndex(USER_COLUMN_LINKED)));
            //user.setTimeupdate(c.getString(c.getColumnIndex(USER_COLUMN_TIME_UPDATE)));
            list.add(user);
        }
        return list;
    }

    public boolean insertUser(UserObject user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_ID, user.getId());
        contentValues.put(USER_COLUMN_IMAGE, user.getImage());
        contentValues.put(USER_COLUMN_FIRST_NAME, user.getFirstName());
        contentValues.put(USER_COLUMN_LAST_NAME, user.getLastName());
        contentValues.put(USER_COLUMN_POSITION, user.getPosition());
        contentValues.put(USER_COLUMN_PHONE_NUMBER_1, user.getPhoneNumber1());
        contentValues.put(USER_COLUMN_PHONE_NUMBER_2, user.getPhoneNumber2());
        contentValues.put(USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(USER_COLUMN_WEBSITE, user.getWebsite());
        contentValues.put(USER_COLUMN_LOCATION, user.getLocation());
        contentValues.put(USER_COLUMN_LINKED, user.getLinkedIn());
        //contentValues.put(USER_COLUMN_TIME_UPDATE, user.getTimeupdate());
        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateUser(UserObject user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_IMAGE, user.getImage());
        contentValues.put(USER_COLUMN_FIRST_NAME, user.getFirstName());
        contentValues.put(USER_COLUMN_LAST_NAME, user.getLastName());
        contentValues.put(USER_COLUMN_POSITION, user.getPosition());
        contentValues.put(USER_COLUMN_PHONE_NUMBER_1, user.getPhoneNumber1());
        contentValues.put(USER_COLUMN_PHONE_NUMBER_2, user.getPhoneNumber2());
        contentValues.put(USER_COLUMN_EMAIL, user.getEmail());
        contentValues.put(USER_COLUMN_WEBSITE, user.getWebsite());
        contentValues.put(USER_COLUMN_LOCATION, user.getLocation());
        contentValues.put(USER_COLUMN_LINKED, user.getLinkedIn());
        //contentValues.put(USER_COLUMN_TIME_UPDATE, user.getTimeupdate());
        db.update(USER_TABLE_NAME, contentValues, USER_COLUMN_ID + " = ? ", new String[]{user.getId()});
        return true;
    }

    public Integer deleteUser(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(USER_TABLE_NAME, USER_COLUMN_ID + " = ? ", new String[]{userId});
    }
}
