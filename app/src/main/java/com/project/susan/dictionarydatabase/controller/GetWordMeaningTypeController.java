package com.project.susan.dictionarydatabase.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.project.susan.dictionarydatabase.MainActivity;
import com.project.susan.dictionarydatabase.database.DatabaseSource;
import com.project.susan.dictionarydatabase.database.WordAndMeaningWithType;
import com.project.susan.dictionarydatabase.util.AppUtil;

import java.util.List;

/**
 * Created by susan on 7/17/14.
 */
public class GetWordMeaningTypeController extends AsyncTask<String, Void, List<WordAndMeaningWithType>> {

    private static final String TAG = GetWordMeaningTypeController.class.getSimpleName();
    private Context context;
    private DatabaseSource databaseSource;

    public GetWordMeaningTypeController(Context context) {
        this.context = context;
        databaseSource = new DatabaseSource(context);
    }

    @Override
    protected List<WordAndMeaningWithType> doInBackground(String... params) {
        String word = params[0];
        databaseSource.open();
        List<WordAndMeaningWithType> wordMeaningList = databaseSource.getWordAndMeaningWithType(word);
        AppUtil.showLog(TAG, wordMeaningList.toString());
        databaseSource.close();
        return wordMeaningList;
    }

    @Override
    protected void onPostExecute(List<WordAndMeaningWithType> wordAndMeaningWithTypes) {
        super.onPostExecute(wordAndMeaningWithTypes);
        ((MainActivity) context).displayResult(wordAndMeaningWithTypes);
    }
}
