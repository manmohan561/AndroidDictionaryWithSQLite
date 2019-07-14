package com.javahelps.com.javahelps.androiddictionarywithsqlite;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.javahelps.com.javahelps.androiddictionarywithsqlite.adapters.Word_Adapter;
import com.javahelps.com.javahelps.androiddictionarywithsqlite.utils.DatabaseHelper;
import com.javahelps.com.javahelps.androiddictionarywithsqlite.utils.DictionaryModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvWord;
    private Word_Adapter word_adapter;
    private List<DictionaryModel> dictionaryModelList;
    private DatabaseHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        rvWord=findViewById( R.id.rvWord);
        rvWord.setLayoutManager(new LinearLayoutManager( getApplicationContext() ) );

        mDBHelper=new DatabaseHelper( this );

        File database =getApplicationContext().getDatabasePath( DatabaseHelper.DBNAME );
        if(database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        dictionaryModelList=mDBHelper.getListWord( " " );
        word_adapter =new Word_Adapter();
        word_adapter.setData( dictionaryModelList );
        rvWord.setAdapter( word_adapter );

    }
    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("Database","Copy Success");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
