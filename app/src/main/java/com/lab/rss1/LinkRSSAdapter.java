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

public class LinkRSSAdapter  extends ArrayAdapter<LinkRSS> {
    private Context mContext;
    private List<LinkRSS> linksRSSLisn = new ArrayList<>();

    public LinkRSSAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<LinkRSS> list) {
        super(context, 0 , list);
        mContext = context;
        linksRSSLisn = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        LinkRSS currentNote = linksRSSLisn.get(position);
        TextView idRSS = (TextView) listItem.findViewById(R.id.idRSS) ;
        idRSS.setText(""+currentNote.getId());
        TextView titleRSS = (TextView)listItem.findViewById(R.id.titleRSS);
        titleRSS.setText(currentNote.getTitle());
        TextView linkRSS = (TextView) listItem.findViewById(R.id.linkRSS);
        linkRSS.setText(currentNote.getLink());

        return listItem;
    }
}
