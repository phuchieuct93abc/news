package com.feed;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rssreader.R;

public class NoteViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;        
    private ArrayList<String> notes;

    public NoteViewAdapter(Context currentContext, ArrayList<String> allNotes) {
        inflater = LayoutInflater.from(currentContext);        
        notes = allNotes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = convertView;

        if (convertView == null) {
            layout = inflater.inflate(R.layout.list_feed_viewer, null);
        }            

        //Get's value from our ArrayList by the position
        String note = notes.get(position);

        TextView tView = (TextView) layout.findViewById(R.id.note);

        tView.setText(note);
        return layout;
    }
}