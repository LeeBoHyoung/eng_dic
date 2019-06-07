package apppractice.cs.skku.pa2.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import apppractice.cs.skku.pa2.R;

public class DefinitionActivity extends AppCompatActivity {

    int change_val;
    ImageView bookmark;
    int pass;
    Intent intent;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        intent = getIntent();
        Bundle bundle = intent.getExtras();


        TextView word = (TextView)findViewById(R.id.txt_def_word);
        TextView wordclass = (TextView)findViewById(R.id.txt_def_class);
        TextView def = (TextView)findViewById(R.id.txt_def_def);
        bookmark = (ImageView)findViewById(R.id.bookmark_change);

        word.setText(bundle.getString("WORD"));
        wordclass.setText(bundle.getString("CLASS"));
        def.setText(bundle.getString("DEF"));
        id = bundle.getInt("ID");
        change_val = bundle.getInt("MARK");


        if(change_val == 1){
            bookmark.setSelected(true);
        }
        else{
            bookmark.setSelected(false);
        }

    }

    public void btn_change_click(View v) {
        if (bookmark.isSelected() == true) {
            bookmark.setSelected(false);
            pass = 0;
        }
        else {
            bookmark.setSelected(true);
            pass = 1;
        }

        intent.putExtra("change", pass);
        intent.putExtra("id", id);

        setResult(RESULT_OK, intent);
    }

    public void btn_back2_click(View v){
        finish();
    }
}
