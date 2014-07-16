package com.project.susan.dictionarydatabase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.susan.dictionarydatabase.util.AppText;
import com.project.susan.dictionarydatabase.util.AppUtil;

/**
 * Created by susan on 7/16/14.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        AppUtil.showLog(TAG, "DatabaseHelper constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AppText.TABLE_WORDS +
                "(" + AppText.COLUMN_WORD_ID + " integer primary key autoincrement, "
                + AppText.COLUMN_WORD + " text not null)");
        AppUtil.showLog(TAG, "table " + AppText.TABLE_WORDS + " created");
        db.execSQL("CREATE TABLE " + AppText.TABLE_MEANINGS +
                "(" + AppText.COLUMN_MEANING_ID + " integer primary key autoincrement, "
                + AppText.COLUMN_MEANING + " text not null, "
                + AppText.COLUMN_WORD_ID + " integer not null, "
                + AppText.COLUMN_TYPE_ID + " integer not null)");
        AppUtil.showLog(TAG, "table " + AppText.TABLE_MEANINGS + " created");
        db.execSQL("create table " + AppText.TABLE_TYPES +
                "(" + AppText.COLUMN_TYPE_ID + " integer primary key autoincrement, "
                + AppText.COLUMN_TYPE + " text not null)");
        AppUtil.showLog(TAG, "table " + AppText.TABLE_TYPES + " created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + AppText.TABLE_WORDS);
        db.execSQL("drop table if exists " + AppText.TABLE_MEANINGS);
        db.execSQL("drop table if exists " + AppText.TABLE_TYPES);
        onCreate(db);
        AppUtil.showLog(TAG, "All database upgraded");
    }
}
