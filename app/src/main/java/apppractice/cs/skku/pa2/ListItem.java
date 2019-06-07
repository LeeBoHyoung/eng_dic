package apppractice.cs.skku.pa2;

import android.widget.ImageView;

public class ListItem {
    private int wid;
    private String wordStr;


    public String getWordStr() {
        return this.wordStr;
    }

    public int getWid(){
        return this.wid;
    }

    public void setWordStr(String wordStr) {
        this.wordStr = wordStr;
    }

    public void setWid(int wid){
        this.wid = wid;
    }

}
