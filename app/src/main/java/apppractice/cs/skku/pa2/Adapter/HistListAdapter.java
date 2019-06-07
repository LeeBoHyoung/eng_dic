package apppractice.cs.skku.pa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import apppractice.cs.skku.pa2.Activity.MainActivity;
import apppractice.cs.skku.pa2.HistListItem;
import apppractice.cs.skku.pa2.ListItem;
import apppractice.cs.skku.pa2.R;

public class HistListAdapter extends BaseAdapter {

    private ArrayList<HistListItem> listViewItemList = new ArrayList<HistListItem>() ;

    public HistListAdapter() { }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hist_list_item, parent, false);
        }


        TextView WordTxt = (TextView) convertView.findViewById(R.id.word_hist);
        ImageButton btn_del = (ImageButton) convertView.findViewById(R.id.btn_del);

        HistListItem listViewItem = listViewItemList.get(position);


        WordTxt.setText(listViewItem.getWordStr());
        //btn_del.setOnClickListener((onClickListener)context);
        btn_del.setTag(pos);
        /*btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context, "test", Toast.LENGTH_SHORT);
                toast.show();
            }
        });*/

        btn_del.setFocusable(false);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }


    public void addItem(String word) {

        HistListItem item = new HistListItem();

        item.setWordStr(word);
        listViewItemList.add(item);
    }

    public void clearList(){
        listViewItemList.clear();
    }

    public void delItem(int pos){
        listViewItemList.remove(pos);
    }
}
