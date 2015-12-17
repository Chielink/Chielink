package jp.peer4.chielink;

//質問作成の画面

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

public class QuestionActivity extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private BootstrapButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("質問する");
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);

        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText("カテゴリー");

        //textView = (TextView) findViewById(R.id.textView1);
        //textView.setText("カテゴリー１");

        //textView = (TextView) findViewById(R.id.textView2);
        //textView.setText("カテゴリー２");

        // textView = (TextView) findViewById(R.id.textView3);
        // textView.setText("タイトル");

        // textView = (TextView) findViewById(R.id.textView4);
        // textView.setText("質問文");

        spinner1 = (Spinner) findViewById(R.id.spinner1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String item = (String) spinner1.getSelectedItem();
                addItemsOnSpinner2(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        addListenerOnButton();
    }

    //spinner2に値をセット
    public void addItemsOnSpinner2(String item){

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        switch (item){
            case "芸能人" :
                list.add("話題の人物");
                list.add("俳優");
                list.add("ミュージシャン");
                list.add("お笑い芸人");
                spinner2.setAdapter(dataAdapter);
                break;

            case "テレビ" :
                list.add("CM");
                list.add("ドラマ");
                list.add("バラエティ");
                list.add("ドキュメンタリー");
                spinner2.setAdapter(dataAdapter);
                break;

            case "音楽" :
                list.add("邦楽");
                list.add("洋楽");
                list.add("K-POP、アジア");
                list.add("クラシック");
                spinner2.setAdapter(dataAdapter);
                break;

            case "料理、レシピ" :
                list.add("レシピ");
                list.add("料理、食材");
                list.add("菓子、スイーツ");
                list.add("お酒、ドリンク");
                spinner2.setAdapter(dataAdapter);
                break;

            case "住宅" :
                list.add("家具、インテリア");
                list.add("収納");
                list.add("DIY");
                list.add("不動産");
                spinner2.setAdapter(dataAdapter);
                break;

            default :
                break;
        }
    }

    //決定が押された時の処理
    public void addListenerOnButton(){

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (BootstrapButton) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v){

                String category = (String) spinner2.getSelectedItem();

                EditText editText1 = (EditText) findViewById(R.id.editText1);
                String title = editText1.getText().toString();

                EditText editText2 = (EditText) findViewById(R.id.editText2);
                String question = editText2.getText().toString();

                ArrayList<String> array = new ArrayList<String>();

                array.add(category);
                array.add(title);
                array.add(question);

                HashMap<String, String> map = new HashMap<String, String>();

                map.put("category", array.get(0));
                map.put("title", array.get(1));
                map.put("question", array.get(2));

                Toast.makeText(getApplicationContext(), map.get("category"), Toast.LENGTH_LONG).show();
            }
        });
    }
}
