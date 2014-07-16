package com.project.susan.dictionarydatabase.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.project.susan.dictionarydatabase.MainActivity;
import com.project.susan.dictionarydatabase.database.DatabaseSource;
import com.project.susan.dictionarydatabase.database.Words;

import java.util.List;

/**
 * Created by susan on 7/17/14.
 */
public class GetWordsFromDatabaseController extends AsyncTask<String, Void, List<Words>> {

    private Context context;
    private DatabaseSource databaseSource;

    public GetWordsFromDatabaseController(Context context) {
        this.context = context;
        this.databaseSource = new DatabaseSource(context);
    }

    @Override
    protected List<Words> doInBackground(String... params) {
        String word = params[0];
        databaseSource.open();

        List<Words> wordList = databaseSource.getAvailableWords(word);

        databaseSource.close();
        return wordList;
    }

    @Override
    protected void onPostExecute(List<Words> wordses) {
        super.onPostExecute(wordses);
        ((MainActivity) context).loadListView(wordses);
    }
}
