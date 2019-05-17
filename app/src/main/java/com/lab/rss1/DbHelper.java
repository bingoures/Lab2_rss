package com.lab.rss1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "RSS";
    public static final String TABLE_NAME = "resource";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_TITLE = "note";
    public static final String COLUMN_LINK = "link";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL);", TABLE_NAME, COLUMN_ID, COLUMN_TITLE, COLUMN_LINK);
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = String.format("DELETE TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewItem(LinkRSS linkRSS){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LINK, linkRSS.getLink());
        values.put(COLUMN_TITLE, linkRSS.getTitle());
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public ArrayList<LinkRSS> getLinksRSS() {
        ArrayList<LinkRSS> linksRSS = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_LINK, COLUMN_TITLE}, null, null,null,null, null);

        while (c.moveToNext()){
            LinkRSS rss = new LinkRSS();
            int index;
            index = c.getColumnIndex(COLUMN_ID);
            rss.setId(c.getInt(index));
            index = c.getColumnIndex(COLUMN_TITLE);
            rss.setTitle(c.getString(index));
            index = c.getColumnIndex(COLUMN_LINK);
            rss.setLink(c.getString(index));
            linksRSS.add(rss);
        }
        c.close();
        db.close();
        return linksRSS;
    }

    public ArrayList<LinkRSS> searchLinksRSS(String keyValue) {
        ArrayList<LinkRSS> linksRSS = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_LINK, COLUMN_TITLE}, COLUMN_TITLE + " LIKE ?", new String[] {"%" + keyValue + "%"},null,null, null);
        while (c.moveToNext()){
            LinkRSS rss = new LinkRSS();
            int index;
            index = c.getColumnIndex(COLUMN_ID);
            rss.setId(c.getInt(index));
            index = c.getColumnIndex(COLUMN_TITLE);
            rss.setTitle(c.getString(index));
            index = c.getColumnIndex(COLUMN_LINK);
            rss.setLink(c.getString(index));
            linksRSS.add(rss);
        }
        c.close();
        db.close();
        return linksRSS;
    }

    public LinkRSS getLinkRSSById(int id){
        LinkRSS linkRSS = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_LINK, COLUMN_TITLE}, COLUMN_ID + " = ?", new String[] {""+id},null,null, null);
        while (c.moveToNext()){
            linkRSS = new LinkRSS();
            int index;
            index = c.getColumnIndex(COLUMN_ID);
            linkRSS.setId(c.getInt(index));
            index = c.getColumnIndex(COLUMN_TITLE);
            linkRSS.setTitle(c.getString(index));
            index = c.getColumnIndex(COLUMN_LINK);
            linkRSS.setLink(c.getString(index));
            break;
        }

        return linkRSS;
    }

    public String getURLById(int id){
        LinkRSS linkRSS = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_LINK}, COLUMN_ID + " = ?", new String[] {""+id},null,null, null);
        while (c.moveToNext()){
            linkRSS = new LinkRSS();
            int index;
            index = c.getColumnIndex(COLUMN_ID);
            linkRSS.setId(c.getInt(index));
            index = c.getColumnIndex(COLUMN_TITLE);
            linkRSS.setTitle(c.getString(index));
            index = c.getColumnIndex(COLUMN_LINK);
            linkRSS.setLink(c.getString(index));
            break;
        }

        return linkRSS.getLink();
    }

    public void deleteLinkRSS(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{""+id});
    }
}
