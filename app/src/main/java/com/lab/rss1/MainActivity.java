package com.lab.rss1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static DbHelper dbHelper;
    private LinkRSSAdapter mAdapter;
    private ListView listView;
    private static  final int REQUEST_ACCESS_TYPE=1;
    private final int IDD_LIST_CATS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        listView = (ListView) findViewById(R.id.movies_list);


        showItemList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.idRSS);
                showLine(tv.getText().toString());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                TextView tv = (TextView) view.findViewById(R.id.idRSS);
                                int id = Integer.valueOf(tv.getText().toString());
                                dbHelper.deleteLinkRSS(id);
                                Toast.makeText(
                                        MainActivity.this, "Источник RSS удален",
                                        Toast.LENGTH_SHORT
                                ).show();
                                showItemList();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                return false;
            }
        });

        /*
        String url = "https://stackoverflow.com/feeds/tag?tagnames=rome";
        SyndFeed feed = null;
        try {
            feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(feed.getTitle());
         */
    }

    public void showLine(String id){
        Intent intent = new Intent(this, ShowLine.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    public void deleteRSS(View view){
        View parent = (View) view.getParent();
        TextView itemTextView = (TextView) parent.findViewById(R.id.idRSS);
        int id = Integer.valueOf(itemTextView.getText().toString());
        dbHelper.deleteLinkRSS(id);
        showItemList();
    }

    private void showItemList() {
        ArrayList<LinkRSS> moviesList = dbHelper.getLinksRSS();
        mAdapter = new LinkRSSAdapter(this, moviesList);
        listView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.addLinkRSS:

                Intent intent = new Intent(this, AddLinkRSS.class);
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        showItemList();
    }

    public static DbHelper getDbHelper() {
        return dbHelper;
    }
}
