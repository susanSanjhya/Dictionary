package com.project.susan.dictionarydatabase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.project.susan.dictionarydatabase.adapters.WordMeaningListAdapter;
import com.project.susan.dictionarydatabase.controller.DatabaseLoaderController;
import com.project.susan.dictionarydatabase.controller.GetWordMeaningTypeController;
import com.project.susan.dictionarydatabase.controller.GetWordsFromDatabaseController;
import com.project.susan.dictionarydatabase.database.WordAndMeaningWithType;
import com.project.susan.dictionarydatabase.database.Words;
import com.project.susan.dictionarydatabase.util.AppText;
import com.project.susan.dictionarydatabase.util.AppUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView listViewAvailableWords;
    private EditText editTextSearch;
    private ArrayAdapter<String> listViewAdapter;
    private ArrayList<String> wordsFromDatabase;
    private List<WordAndMeaningWithType> wordMeaningList;
    private ListView listViewMeaningType;
    private WordMeaningListAdapter adapter;
    private TextView textViewWord;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(AppText.PREF_KEY, 0);
        boolean dbLoaded = sharedPreferences.getBoolean(AppText.KEY_IS_DB_LOADED, false);
        if (!dbLoaded) {
            new DatabaseLoaderController(this).execute();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(AppText.KEY_IS_DB_LOADED, true);
            editor.commit();
            AppUtil.showLog(TAG, "DATABASE NOT LOADED");
        } else {
            AppUtil.showLog(TAG, "DATABASE ALREADY LOADED");
        }
        wordsFromDatabase = new ArrayList<String>();
        wordMeaningList = new ArrayList<WordAndMeaningWithType>();

        textViewWord = (TextView) findViewById(R.id.textViewWord);

        listViewAvailableWords = (ListView) findViewById(R.id.listViewAvailableWords);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordsFromDatabase);
        listViewAvailableWords.setAdapter(listViewAdapter);
        listViewAvailableWords.setOnScrollListener(myScrollListener);
        listViewAvailableWords.setOnItemClickListener(this);

        listViewMeaningType = (ListView) findViewById(R.id.listViewMeaningAndType);
        adapter = new WordMeaningListAdapter(this, wordMeaningList);
        listViewMeaningType.setAdapter(adapter);

        editTextSearch.addTextChangedListener(myTextWatcher);
    }

    private AbsListView.OnScrollListener myScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            AppUtil.hideKeyboard(MainActivity.this, editTextSearch);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    private TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            new GetWordsFromDatabaseController(MainActivity.this).execute(s.toString());
        }
    };

    public void loadListView(List<Words> wordsList) {

        textViewWord.setVisibility(View.GONE);
        listViewMeaningType.setVisibility(View.GONE);
        listViewAvailableWords.setVisibility(View.VISIBLE);

        wordsFromDatabase.clear();
        for (int i = 0; i < wordsList.size(); i++) {
            wordsFromDatabase.add(i, wordsList.get(i).getWord());
        }
        listViewAdapter.notifyDataSetChanged();
    }

    public void displayResult(List<WordAndMeaningWithType> wordMeaningList1) {
        wordMeaningList.clear();
        wordMeaningList = wordMeaningList1;
        for (int i = 0; i < wordMeaningList.size(); i++) {
            AppUtil.showLog(TAG, wordMeaningList.get(i).getMeaning() + "\t" + wordMeaningList.get(i).getType());
        }
        adapter = null;
        adapter = new WordMeaningListAdapter(this, wordMeaningList);
        listViewMeaningType.setAdapter(adapter);
        listViewMeaningType.setVisibility(View.VISIBLE);
        AppUtil.hideKeyboard(MainActivity.this, editTextSearch);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String word = wordsFromDatabase.get(position);
        textViewWord.setVisibility(View.VISIBLE);
        listViewAvailableWords.setVisibility(View.GONE);
        textViewWord.setText(word);
        new GetWordMeaningTypeController(MainActivity.this).execute(word);
        //to do
    }
}
