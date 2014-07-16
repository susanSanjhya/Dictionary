package com.project.susan.dictionarydatabase.database;

/**
 * Created by susan on 7/16/14.
 */
public class Words {
    private long _id;
    private String word;
    private long typeId;

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }
}
