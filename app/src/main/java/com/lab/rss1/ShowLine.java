package com.lab.rss1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class ShowLine extends AppCompatActivity {

    public int id;
    private ListView listView;

    private static  final int REQUEST_ACCESS_TYPE=1;

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_line);
        listView = (ListView) findViewById(R.id.show_line);
        this.id = Integer.valueOf(getIntent().getStringExtra("id"));
        String url = MainActivity.getDbHelper().getURLById(id);
        AsyncTaskURL a = (AsyncTaskURL) new AsyncTaskURL().execute(url);
        try {
            a.showItemList(this, listView);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.linkRSS);
                showBrowser(tv.getText().toString());
            }
        });

    }

    public void showBrowser(String id){
        Intent intent = new Intent(this, BrowserAct.class);
        intent.putExtra("link", id);

        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    public void f(){
        String rss = "http://pravo.by/novosti/obshchestvenno-politicheskie-i-v-oblasti-prava/rss/";

        Log.d("DEBUG", "------------------------Entered:" + rss + "-----" + isOnline());
        URL feedUrl = null;
        try {
            feedUrl = new URL(rss);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ArrayAdapter adapter = null;
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
        Toast.makeText(this, "#Feeds retrieved: " + entries.size(), Toast.LENGTH_SHORT).show();

        Iterator iterator = entries.listIterator();
        while (iterator.hasNext())
        {
            SyndEntry ent = (SyndEntry) iterator.next();
            String title = ent.getTitle();
            adapter.add(title);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_rss_list, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.back:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
