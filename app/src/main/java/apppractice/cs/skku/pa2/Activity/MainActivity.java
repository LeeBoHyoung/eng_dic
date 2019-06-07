package apppractice.cs.skku.pa2.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import apppractice.cs.skku.pa2.Adapter.HistListAdapter;
import apppractice.cs.skku.pa2.Adapter.ListAdapter;
import apppractice.cs.skku.pa2.CsvReader;
import apppractice.cs.skku.pa2.DB.DBHelper;
import apppractice.cs.skku.pa2.HistListItem;
import apppractice.cs.skku.pa2.ListItem;
import apppractice.cs.skku.pa2.R;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListAdapter listAdapter = new ListAdapter();
    EditText search;
    DBHelper dbHelper;
    SQLiteDatabase mDB;
    HistListAdapter listHistory;
    ListView listView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream inputStream = getResources().openRawResource(R.raw.dic_a);
        CsvReader csvFile = new CsvReader(inputStream);
        List file = csvFile.read();

        String dbName = "dic.db";
        int dbVersion = 1;
        dbHelper = new DBHelper(this, dbName, null, dbVersion);

        mDB = dbHelper.getWritableDatabase();

        //최초 1회만 사전 생성
        String count = "SELECT count(*) FROM dictable";
        Cursor mcursor = mDB.rawQuery(count, null);
        mcursor.moveToFirst();
        if(mcursor.getInt(0) == 0) {

            mDB.beginTransaction();

            try {
                for (int i = 0; i < file.size(); i++) {
                    String[] strData = (String[]) file.get(i);
                    strData[0] = strData[0].substring(0, strData[0].length() - 1);
                    insertDictionary(strData[0], strData[1], strData[2], 0);
                }
                mDB.setTransactionSuccessful();
            } finally {
                mDB.endTransaction();
            }
        }
        mDB.close();

        //deletehistory();

        listHistory = new HistListAdapter();
        Cursor p = searchHistory();
        while(p.moveToNext()){
            listHistory.addItem(p.getString(1));
        }

        final ImageView img_search = (ImageView)findViewById(R.id.btn_search);
        listView2 = (ListView)findViewById(R.id.list_hist);

        //검색 히스토리
        search = (EditText)findViewById(R.id.txt_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                listView2.setVisibility(View.VISIBLE);

                listView2.setAdapter(listHistory);

            }
        });

        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String txt_word = ((HistListItem)listHistory.getItem(position)).getWordStr();
                search.setText(txt_word);

                btn_search_click(img_search);

            }
        });


        //리스트뷰 클릭 시 단어 상세 뷰
        listView = (ListView)findViewById(R.id.list_search);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DefinitionActivity.class);

                int wid = ((ListItem)listAdapter.getItem(position)).getWid();
                String widstr = Integer.toString(wid);
                mDB = dbHelper.getReadableDatabase();
                String sql = "SELECT * FROM dictable WHERE ID = " + widstr;
                Cursor p = mDB.rawQuery(sql, null);
                p.moveToFirst();

                Bundle bundle = new Bundle();
                bundle.putInt("ID", p.getInt(0));
                bundle.putString("WORD", p.getString(1));
                bundle.putString("CLASS", p.getString(2));
                bundle.putString("DEF", p.getString(3));
                bundle.putInt("MARK", p.getInt(4));


                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

    }

    public void btn_bookmark_click(View v){
        Intent intent = new Intent(this, BookMarkActivity.class);
        startActivity(intent);
    }

    public void btn_search_click(View v){
        listView2.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        listAdapter.clearList();

        String search_word = search.getText().toString();

        //검색 히스토리 저장
        insertHistory(search_word);

        //단어 검색
        Cursor cursor = searchWord(search_word);
        int i = 0;
        while(cursor.moveToNext()){
            if(i>4) break;
            else{
                int id = cursor.getInt(0);
                String word = cursor.getString(1);

                listAdapter.addItem(id, word);
                i++;
            }
        }
        listView.setAdapter(listAdapter);

    }

    public void insertDictionary(String word, @Nullable String wordclass, String def, int bookmark){
        String sql = "insert into dictable(WORD, CLASS, DEF, BOOKMARK) values('"+word+"','"+wordclass+"','"+def+"'," + bookmark +")";
        mDB.execSQL(sql);
    }

    public Cursor searchWord(String word){
        mDB = dbHelper.getReadableDatabase();
        String sql = "select * from dictable where WORD LIKE '" + word + "%'";
        Cursor cursor = mDB.rawQuery(sql, null);
        return cursor;
    }

    public void updateBookmark(String id, String bookmark){
        mDB = dbHelper.getWritableDatabase();
        String sql = "update dictable set BOOKMARK = "+bookmark+" where ID ="+id;
        mDB.execSQL(sql);
    }

    public void insertHistory(String word){
        Cursor p = searchHistory();
        int flag = 0;
        while(p.moveToNext()){
            if(p.getString(1).toLowerCase().equals(word.toLowerCase()))
                flag = 1;
        }

        if(flag == 0) {
            mDB = dbHelper.getWritableDatabase();
            String sql = "insert into historytable(HIS_WORD) values('" + word + "')";
            mDB.execSQL(sql);
            listHistory.addItem(word);
        }
    }

    public Cursor searchHistory(){
        mDB = dbHelper.getReadableDatabase();
        String sql = "select * from historytable";
        Cursor cursor = mDB.rawQuery(sql, null);
        return cursor;
    }

    public void deletehistory(String word) {
        mDB = dbHelper.getWritableDatabase();
        String sql = "delete from historytable where HIS_WORD = '" + word + "'";
        mDB.execSQL(sql);
    }

    public void btn_del_click(View v){

        //Histroy 삭제
         int pos = (Integer)v.getTag();
         String word_del = ((HistListItem)listHistory.getItem(pos)).getWordStr();
         deletehistory(word_del);

         listHistory.delItem(pos);
         listHistory.notifyDataSetChanged();

         listView2.setAdapter(listHistory);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Bundle bundle2 = new Bundle();
                bundle2 = data.getExtras();
                String mark = Integer.toString(bundle2.getInt("change"));
                String id = Integer.toString(bundle2.getInt("id"));
                updateBookmark(id, mark);
            }
        }
    }
}
