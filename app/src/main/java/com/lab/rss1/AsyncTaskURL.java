package com.lab.rss1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsyncTaskURL extends AsyncTask<String, Void, Void> {

    private Exception exception;

    private ArrayList<RSSItem> linkRSSArrayList;

    private RSSItemAdapter mAdapter;
    //private ListView listView;

    public boolean ok = false;

    @Override
    protected Void doInBackground(String... urls){
        ok = false;
        String rss = urls[0];

        Log.d("DEBUG", "------------------------Entered:" + rss + "-----");
        URL feedUrl = null;
        try {
            feedUrl = new URL(rss);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        linkRSSArrayList = new ArrayList<>();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new XmlReader(feedUrl));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List entries = feed.getEntries();
        //Toast.makeText(this, "#Feeds retrieved: " + entries.size(), Toast.LENGTH_SHORT).show();



        Iterator iterator = entries.listIterator();
        while (iterator.hasNext())
        {
            SyndEntry ent = (SyndEntry) iterator.next();
            String title = ent.getTitle();
            linkRSSArrayList.add(new RSSItem(ent.getTitle(), ent.getDescription().getValue().replaceAll("\\<.*?>", ""), ent.getLink(), ent.getPublishedDate().toString()));
        }

        for (RSSItem r : linkRSSArrayList)
        {
            Log.d("MyLog", "============================="+ r.getTitle());
            Log.d("MyLog", "============================="+ r.getLink());
            Log.d("MyLog", "============================="+ r.getPubDate());
            Log.d("MyLog", "============================="+ r.getDescription());
        }

        //showItemList(linkRSSArrayList);
        //adapter.notifyDataSetChanged();
        ok = true;
        return null;
    }

    public void showItemList(Context context, ListView lw) throws InterruptedException {
        while(!ok){

        }
        ArrayList<RSSItem> moviesList = linkRSSArrayList;
        mAdapter = new RSSItemAdapter(context, moviesList);
        lw.setAdapter(mAdapter);

    }
}
