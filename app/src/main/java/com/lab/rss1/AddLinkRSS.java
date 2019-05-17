package com.lab.rss1;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddLinkRSS extends AppCompatActivity {

    EditText title;
    EditText link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link_rss);

        title = findViewById(R.id.title);
        link = findViewById(R.id.link);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_addlinkrss, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.insert:
                if (title.getText().length() > 0 && link.getText().length() > 0){
                    LinkRSS linkRSS = new LinkRSS();
                    linkRSS.setTitle(title.getText().toString());
                    linkRSS.setLink(link.getText().toString());
                    if(MainActivity.getDbHelper() != null){
                        MainActivity.getDbHelper().insertNewItem(linkRSS);
                        Toast.makeText(
                                AddLinkRSS.this, "Ссылка на RSS добавлена",
                                Toast.LENGTH_SHORT
                        ).show();
                        setResult(RESULT_CANCELED);
                        finish();
                    } else {
                        Toast.makeText(
                                AddLinkRSS.this, "Ошибка добавления",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    Toast.makeText(
                            AddLinkRSS.this, "Не все поля заполнены",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
