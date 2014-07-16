package com.project.susan.dictionarydatabase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.project.susan.dictionarydatabase.R;
import com.project.susan.dictionarydatabase.database.WordAndMeaningWithType;
import com.project.susan.dictionarydatabase.util.AppUtil;

import java.util.List;

/**
 * Created by susan on 7/17/14.
 */
public class WordMeaningListAdapter extends BaseAdapter {

    private static final String TAG = WordMeaningListAdapter.class.getSimpleName();
    private Context context;
    private List<WordAndMeaningWithType> wordAndMeaningWithTypeList;

    public WordMeaningListAdapter(Context context, List<WordAndMeaningWithType> wordAndMeaningWithTypeList) {

        this.context = context;
        this.wordAndMeaningWithTypeList = wordAndMeaningWithTypeList;
        AppUtil.showLog(TAG, "constructor");
    }

    @Override
    public int getCount() {
        return wordAndMeaningWithTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordAndMeaningWithTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView textViewType, textViewMeaning;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.layout_meaning_type_single_row, parent, false);
            holder.textViewMeaning = (TextView) convertView.findViewById(R.id.textViewMeaning);
            holder.textViewType = (TextView) convertView.findViewById(R.id.textViewType);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppUtil.showLog(TAG, "meaning " + wordAndMeaningWithTypeList.get(position).getMeaning());
        holder.textViewMeaning.setText(wordAndMeaningWithTypeList.get(position).getMeaning());

        AppUtil.showLog(TAG, "Type" + wordAndMeaningWithTypeList.get(position).getType());
        holder.textViewType.setText(wordAndMeaningWithTypeList.get(position).getType());

        convertView.setEnabled(false);
        convertView.setOnClickListener(null);

        return convertView;
    }
}
