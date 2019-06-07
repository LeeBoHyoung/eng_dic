package apppractice.cs.skku.pa2.DB;

import android.provider.BaseColumns;

public final class DataBases {
    public static final class CreateDB implements BaseColumns{
        //dictionary
        //public static final String WORD ="word";
        //public static final String CLASS ="wordclass";
        //public static final String DEF = "definition";
        //public static final int BOOKMARK = 0;
        //public static final String _TABLENAME0 = "dictable";
        public static final String _CREAT0 = "create table if not exists dictable(" +
                "ID integer primary key autoincrement," +
                "WORD text not null," +
                "CLASS text," +
                "DEF text not null," +
                "BOOKMARK integer not null);";

        //history
        //public static final String HIS_WORD ="word";
        //public static final String _TABLENAME1 = "historytable";
        public static final String _CREAT1 = "create table if not exists historytable(" +
                "ID integer primary key autoincrement," +
                "HIS_WORD text not null);";
    }
}
