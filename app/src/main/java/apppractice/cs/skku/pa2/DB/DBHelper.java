package apppractice.cs.skku.pa2.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBases.CreateDB._CREAT0);
        db.execSQL(DataBases.CreateDB._CREAT1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS historytable");
        onCreate(db);
    }
}
/*
    public DBHelper(Context context){
        this.mCtx = context;
    }

    public DBHelper open() throws SQLException{
        mDBHelper = new DataBaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();

        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    public void insertDictionary(String word, @Nullable String wordclass, String def, int bookmark){
        String sql = "insert into dictable(WORD, CLASS, DEF, BOOKMARK) values('"+word+"','"+wordclass+"','"+def+"'," + bookmark +")";
        mDB.execSQL(sql);
    }

    public void insertHistory(String word){
        mDB = mDBHelper.getWritableDatabase();
        String sql = "insert into historytable values('"+word+"')";
        mDB.execSQL(sql);

        //ContentValues values = new ContentValues();
        //values.put(DataBases.CreateDB.HIS_WORD, word);

        //return mDB.insert(DataBases.CreateDB._TABLENAME1, null, values);
    }

    //public Cursor selectDictionary(){
    //    return mDB.query(DataBases.CreateDB._TABLENAME0,)
    //}

    public Cursor searchWord(String word){
        mDB = mDBHelper.getReadableDatabase();
        String sql = "select * from dictable where WORD LIKE '" + word + "%'";
        Cursor cursor = mDB.rawQuery(sql, null);
        return cursor;
    }

    public Cursor searchBookmark(String bookmark){
        mDB = mDBHelper.getReadableDatabase();
        String sql = "select * from dictable where BOOKMARK =" + bookmark;
        Cursor cursor = mDB.rawQuery(sql, null);
        return cursor;
    }

    public void updateBookmark(String id, String bookmark){
        mDB = mDBHelper.getReadableDatabase();
        String sql = "update dictable set BOOKMARK = "+bookmark+"where _ID ="+id;
        mDB.execSQL(sql);
    }

    public void deletehistory() {
        mDB.delete("historytable", null, null);
    }
}*/
