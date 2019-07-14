package com.javahelps.com.javahelps.androiddictionarywithsqlite.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME ="dictionary.db";
    public static final String DBLOCATION ="/data/data/com.javahelps.com.javahelps.androiddictionarywithsqlite/database";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super( context,DBNAME,null, 1 );
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void openDatabase(){
        String dbPath =mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen())
        {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    public void closeDatabase() {
        if(mDatabase!=null) {
            mDatabase.close();
        }
    }
    public List<DictionaryModel> getListWord(String wordSearch){
        DictionaryModel dictionaryModel=null;
        List<DictionaryModel> dictionaryModelList=new ArrayList<>( );
        openDatabase();
        String[] args={"%"+wordSearch+"%"};


        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Dictionary1 Where word like ?", args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            dictionaryModel = new DictionaryModel( cursor.getString(0), cursor.getString(1), cursor.getString(2));
            dictionaryModelList.add(dictionaryModel);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return dictionaryModelList;
    }

}
