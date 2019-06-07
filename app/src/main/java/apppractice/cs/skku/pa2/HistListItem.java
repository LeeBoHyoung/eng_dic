package apppractice.cs.skku.pa2;

import android.view.View;

public class HistListItem {
    private String wordStr;
    private View.OnClickListener onClickListener;


    public String getWordStr() {
        return this.wordStr;
    }

    public void setWordStr(String wordStr) {
        this.wordStr = wordStr;
    }

}
