package com.project.susan.dictionarydatabase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.project.susan.dictionarydatabase.util.AppText;
import com.project.susan.dictionarydatabase.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susan on 7/16/14.
 */
public class DatabaseSource {

    private static final String TAG = DatabaseSource.class.getSimpleName();
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private String[] tableWordColumns = {AppText.COLUMN_WORD};

    public DatabaseSource(Context context) {
        AppUtil.showLog(TAG, "DatabaseSource constructor");
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addWord(String word) {
        ContentValues values = new ContentValues();
        values.put(AppText.COLUMN_WORD, word);
        long insertId = db.insert(AppText.TABLE_WORDS, null, values);
        if (insertId > 0) {
            AppUtil.showLog(TAG, word + " added to table " + AppText.TABLE_WORDS
                    + " with insert id " + insertId);
        } else {
            AppUtil.showLog(TAG, word + " insert failed");
        }
    }

    public void addMeaning(int wordId, String meaning, int typeId) {
        ContentValues values = new ContentValues();
        values.put(AppText.COLUMN_MEANING, meaning);
        values.put(AppText.COLUMN_WORD_ID, wordId);
        values.put(AppText.COLUMN_TYPE_ID, typeId);
        long insertId = db.insert(AppText.TABLE_MEANINGS, null, values);
        if (insertId > 0) {
            AppUtil.showLog(TAG, meaning + ", " + wordId + ", " + typeId + " added to table " +
                    AppText.TABLE_MEANINGS + " with insert id " + insertId);
        } else {
            AppUtil.showLog(TAG, "insert failed to table " + AppText.TABLE_MEANINGS);
        }
    }

    public void addType(String type) {
        ContentValues values = new ContentValues();
        values.put(AppText.COLUMN_TYPE, type);
        long insertId = db.insert(AppText.TABLE_TYPES, null, values);

        if (insertId > 0) {
            AppUtil.showLog(TAG, type + " added to table " + AppText.TABLE_TYPES);
        } else {
            AppUtil.showLog(TAG, type + " insert failed");
        }
    }

    public List<Words> getAvailableWords(String word) {

        Cursor cursor = db.rawQuery("select " + AppText.COLUMN_WORD + " from " + AppText.TABLE_WORDS
                + " where " + AppText.COLUMN_WORD + " like '" + word + "%'", null);
        List<Words> wordsList = new ArrayList<Words>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Words words = cursorToWords(cursor);
            wordsList.add(words);
            cursor.moveToNext();
        }
        cursor.close();
        return wordsList;
    }

    public List<WordAndMeaningWithType> getWordAndMeaningWithType(String word) {
        AppUtil.showLog(TAG, "Query is " + "select w." + AppText.COLUMN_WORD
                + ", m." + AppText.COLUMN_MEANING + ", "
                + "t." + AppText.COLUMN_TYPE
                + " from " + AppText.TABLE_WORDS + " w, " + AppText.TABLE_MEANINGS + " m, "
                + AppText.TABLE_TYPES + " t "
                + " where w." + AppText.COLUMN_WORD_ID + " = m." + AppText.COLUMN_WORD_ID
                + " and t." + AppText.COLUMN_TYPE_ID + " = m." + AppText.COLUMN_TYPE_ID
                + " and w." + AppText.COLUMN_WORD + " = '" + word + "'");
        Cursor cursor = db.rawQuery("select w." + AppText.COLUMN_WORD
                + ", m." + AppText.COLUMN_MEANING + ", "
                + "t." + AppText.COLUMN_TYPE
                + " from " + AppText.TABLE_WORDS + " w, " + AppText.TABLE_MEANINGS + " m, "
                + AppText.TABLE_TYPES + " t "
                + " where w." + AppText.COLUMN_WORD_ID + " = m." + AppText.COLUMN_WORD_ID
                + " and t." + AppText.COLUMN_TYPE_ID + " = m." + AppText.COLUMN_TYPE_ID
                + " and w." + AppText.COLUMN_WORD + " = '" + word + "'", null);

        List<WordAndMeaningWithType> wordAndMeaningList = new ArrayList<WordAndMeaningWithType>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WordAndMeaningWithType wordAndMeaningWithType = cursorToWordAndMeaningWithType(cursor);
            wordAndMeaningList.add(wordAndMeaningWithType);
            cursor.moveToNext();
        }
        cursor.close();
        return wordAndMeaningList;
    }

    private WordAndMeaningWithType cursorToWordAndMeaningWithType(Cursor cursor) {
        WordAndMeaningWithType wordAndMeaningWithType = new WordAndMeaningWithType();
        wordAndMeaningWithType.setType(cursor.getString(2));
        wordAndMeaningWithType.setWord(cursor.getString(0));
        wordAndMeaningWithType.setMeaning(cursor.getString(1));
        return wordAndMeaningWithType;
    }

    private Words cursorToWords(Cursor cursor) {
        Words words = new Words();
        words.setWord(cursor.getString(0));
        return words;
    }
}
