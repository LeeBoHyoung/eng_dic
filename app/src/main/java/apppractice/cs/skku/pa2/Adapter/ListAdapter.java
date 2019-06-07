package apppractice.cs.skku.pa2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apppractice.cs.skku.pa2.ListItem;
import apppractice.cs.skku.pa2.R;

public class ListAdapter extends BaseAdapter {

    private ArrayList<ListItem> listViewItemList = new ArrayList<ListItem>() ;

    public ListAdapter() { }

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
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }


        TextView WordTxt = (TextView) convertView.findViewById(R.id.word_txt);


        ListItem listViewItem = listViewItemList.get(position);

        WordTxt.setText(listViewItem.getWordStr());


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


    public void addItem(int id, String word) {
        ListItem item = new ListItem();

        item.setWordStr(word);
        item.setWid(id);

        listViewItemList.add(item);
    }

    public void clearList(){
        listViewItemList.clear();
    }

    public void delItem(int pos){
        listViewItemList.remove(pos);
    }
}