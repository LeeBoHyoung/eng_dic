package apppractice.cs.skku.pa2.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import apppractice.cs.skku.pa2.Adapter.ListAdapter;
import apppractice.cs.skku.pa2.DB.DBHelper;
import apppractice.cs.skku.pa2.ListItem;
import apppractice.cs.skku.pa2.R;

public class BookMarkActivity extends AppCompatActivity {
    ListView listView;
    ListAdapter listAdapter = new ListAdapter();
    DBHelper dbHelper;
    SQLiteDatabase mDB;
    //int[] ids = new int[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        listView = (ListView)findViewById(R.id.list_bookmark);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDB = dbHelper.getReadableDatabase();
                //String str_id = Integer.toString(ids[position]);
                String str_id = Integer.toString(((ListItem)listAdapter.getItem(position)).getWid());
                String sql = "select * from dictable where ID =" + str_id;

                Cursor cursor = mDB.rawQuery(sql, null);
                int bid, bookmark; String word, wordclass, def;
                    cursor.moveToNext();
                    bid = cursor.getInt(0);
                    word = cursor.getString(1);
                    wordclass = cursor.getString(2);
                    def = cursor.getString(3);
                    bookmark = cursor.getInt(4);

                Bundle bundle = new Bundle();
                bundle.putInt("ID", bid);
                bundle.putString("WORD", word);
                bundle.putString("CLASS", wordclass);
                bundle.putString("DEF", def);
                bundle.putInt("MARK", bookmark);
                //bundle.putInt("pos", position);

                Intent intent = new Intent(getApplicationContext(), DefinitionActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);
            }
        });

        dbHelper = new DBHelper(this, "dic.db", null, 1);
        mDB = dbHelper.getReadableDatabase();

        Cursor p = searchBookmark("1");
        //int i = 0;
        while(p.moveToNext()){
            int wid = p.getInt(0);
            String word = p.getString(1);
            //ids[i] = p.getInt(0);
            listAdapter.addItem(wid, word);
            //i++;
        }
        listView.setAdapter(listAdapter);
    }

    public Cursor searchBookmark(String bookmark){
        mDB = dbHelper.getReadableDatabase();
        String sql = "select * from dictable where BOOKMARK =" + bookmark;
        Cursor cursor = mDB.rawQuery(sql, null);
        return cursor;
    }

    public void btn_back_click(View v){
        finish();
    }

    public void updateBookmark(String id, String bookmark){
        mDB = dbHelper.getWritableDatabase();
        String sql = "update dictable set BOOKMARK = "+bookmark+" where ID ="+id;
        mDB.execSQL(sql);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Bundle bundle2 = new Bundle();
                bundle2 = data.getExtras();
                String mark = Integer.toString(bundle2.getInt("change"));
                String id = Integer.toString(bundle2.getInt("id"));
                //int pos = bundle2.getInt("pos");
                updateBookmark(id, mark);

                Cursor p = searchBookmark("1");

                listAdapter.clearList();
                //int i = 0;
                while(p.moveToNext()){
                    String word = p.getString(1);
                    int wid = p.getInt(0);
                    listAdapter.addItem(wid, word);
                    //i++
                }
                listView.setAdapter(listAdapter);
            }
        }
    }
}
