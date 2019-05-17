package com.lab.rss1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RSSItemAdapter  extends ArrayAdapter<RSSItem> {
    private Context mContext;
    private List<RSSItem> linksRSSList;

    public RSSItemAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<RSSItem> list) {
        super(context, 0 , list);
        mContext = context;
        linksRSSList = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_rss,parent,false);

        RSSItem currentNote = linksRSSList.get(position);
        TextView titleRSS = (TextView) listItem.findViewById(R.id.titleRSS) ;
        titleRSS.setText(currentNote.getTitle());
        TextView descriptionRSS = (TextView)listItem.findViewById(R.id.descriptionRSS);
        descriptionRSS.setText(currentNote.getDescription());
        TextView dateRSS = (TextView) listItem.findViewById(R.id.dateRSS);
        dateRSS.setText(currentNote.getPubDate());
        TextView linkRSS = (TextView) listItem.findViewById(R.id.linkRSS);
        linkRSS.setText(currentNote.getLink());

        return listItem;
    }
}
