package com.project.susan.dictionarydatabase.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.project.susan.dictionarydatabase.database.DatabaseSource;

/**
 * Created by susan on 7/16/14.
 */
public class DatabaseLoaderController extends AsyncTask<Void, Void, Void> {
    private Context context;
    private ProgressDialog progressDialog;
    private DatabaseSource databaseSource;

    public DatabaseLoaderController(Context context) {
        this.context = context;
        databaseSource = new DatabaseSource(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading words and meaning. Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        databaseSource.open();
        databaseSource.addType("noun");
        databaseSource.addType("adjective");
        databaseSource.addType("verb");
        databaseSource.addType("adverb");

        databaseSource.addWord("A");
        databaseSource.addMeaning(1, "the first letter of the English alphabet: from the Greek alpha, a borrowing from the Phoenician", 1);
        databaseSource.addMeaning(1, "one; one sort of: we planted a tree", 2);

        databaseSource.addWord("Abandon");
        databaseSource.addMeaning(2, "to give up (something) completely or forever: to abandon all hope", 3);
        databaseSource.addMeaning(2, "unrestrained freedom of action or emotion; surrender to one's impulses: to shout in wild abandon", 3);

        databaseSource.addWord("Abandoned");
        databaseSource.addMeaning(3, "given up; forsaken; deserted", 2);

        databaseSource.addWord("Ability");
        databaseSource.addMeaning(4, "a being able; power to do (something physical or mental)", 1);

        databaseSource.addWord("Able");
        databaseSource.addMeaning(5, "having enough power, skill, etc. to do something: able to read", 2);

        databaseSource.addWord("About");
        databaseSource.addMeaning(6, "on every side; all around: look about", 4);
        databaseSource.addMeaning(6, "in the vicinity; prevalent: typhoid is about", 2);

        databaseSource.addWord("Above");
        databaseSource.addMeaning(7, "in, at, or to a higher place; overhead; up", 4);
        databaseSource.addMeaning(7, "placed, found, mentioned, etc. above or earlier: as stated in the above rules", 2);

        databaseSource.addWord("Abroad");
        databaseSource.addMeaning(8, "broadly; far and wide", 4);

        databaseSource.addWord("Bus");
        databaseSource.addMeaning(9, "a large, long motor vehicle designed to carry many passengers, usually along a regular route; omnibus", 1);
        databaseSource.addMeaning(9, "to transport by bus; specif., to transport (children) by busing", 3);
        databaseSource.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }
}
