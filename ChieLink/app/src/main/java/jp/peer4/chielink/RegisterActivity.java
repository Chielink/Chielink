package jp.peer4.chielink;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {
    //private SharedPreferences preference;
    //private SharedPreferences.Editor editor;
    private static final int REQUEST_GALLERY = 0;

    private Uri m_uri;
    private static final int REQUEST_CHOOSER = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("プロフィール登録");
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);

        BootstrapButton button_submit = (BootstrapButton)findViewById(R.id.button_submit);
        button_submit.setOnClickListener(clicked);
        ImageView user_thumb = (ImageView)findViewById(R.id.user_thumb);
        user_thumb.setOnClickListener(clicked);
        EditText edittext_name = (EditText)findViewById(R.id.edittext_name);
        EditText edittext_profile = (EditText)findViewById(R.id.edittext_profile);

        //SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        edittext_name.setText(pref.getString("save_name", null), TextView.BufferType.NORMAL);
        edittext_profile.setText(pref.getString("save_profile", null), TextView.BufferType.NORMAL);

        //preference = getSharedPreferences("Preference Name", MODE_PRIVATE);
        //editor = preference.edit();
        //SharedPreferences pref = getSharedPreferences("save_thumb", Context.MODE_PRIVATE);
        //String s = pref.getString("save_thumb", "");
        //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String s = pref.getString("save_thumb", "");
        Toast t_thumb = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);

        try {
            if(/*preference.getBoolean("Launched", false)!=false || */!s.equals("")) {
                byte[] b = Base64.decode(s, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                user_thumb.setImageBitmap(bmp);
                //t_thumb.show();
            }else{
                user_thumb.setImageResource(R.drawable.icon);
                //editor.putBoolean("Launched", true);
                //editor.commit();
            }
        }catch (Exception e){
            Toast t = Toast.makeText(getApplicationContext(), "not thumb", Toast.LENGTH_LONG);
            t.show();
        }
    }

    public View.OnClickListener clicked = new View.OnClickListener(){
        public void onClick(View v){
            switch(v.getId()) {
                case R.id.button_submit:
                    EditText edittext_name = (EditText) findViewById(R.id.edittext_name);
                    EditText edittext_profile = (EditText) findViewById(R.id.edittext_profile);

                    if(edittext_name.getText().toString().equals("") || edittext_profile.getText().toString().equals("")) {
                        Toast t = Toast.makeText(getApplicationContext(), "入力されていない欄があります", Toast.LENGTH_LONG);
                        t.show();
                    }else{
                        String name = edittext_name.getText().toString();
                        String profile = edittext_profile.getText().toString();

                        Toast toast_name = Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG);
                        Toast toast_profile = Toast.makeText(getApplicationContext(), profile, Toast.LENGTH_LONG);

                        //toast_name.show();
                        //toast_profile.show();

                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        pref.edit().putString("save_name", edittext_name.getText().toString()).commit();
                        pref.edit().putString("save_profile", edittext_profile.getText().toString()).commit();
                    }

                    Intent intentoo = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intentoo);

                    break;
                case R.id.user_thumb:
                    //Toast toast_thumb = Toast.makeText(getApplicationContext(), "user_thumb", Toast.LENGTH_LONG);
                    //toast_thumb.show();

                    // ギャラリー呼び出し
                    Intent intent = new Intent();
                    //ImageView user_thumb = (ImageView)findViewById(R.id.user_thumb);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_GALLERY);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView user_thumb = (ImageView)findViewById(R.id.user_thumb);
        user_thumb.setImageResource(R.drawable.icon);

        if(requestCode == REQUEST_GALLERY) {

            if(resultCode != RESULT_OK) {
                // キャンセル時
                user_thumb.setImageResource(R.drawable.icon);
                return ;
            }

            Uri resultUri = (data != null ? data.getData() : m_uri);
            m_uri = resultUri;

            if(resultUri == null) {
                // 取得失敗
                user_thumb.setImageResource(R.drawable.icon);
                return;
            }

            // ギャラリーへスキャンを促す
            MediaScannerConnection.scanFile(
                    this,
                    new String[]{resultUri.getPath()},
                    new String[]{"image/jpeg"},
                    null
            );

            // 画像を設定
            //sp.edit().putString("save_uri", resultUri.toString()).commit();
            try {
                ContentResolver contentResolver = getContentResolver();
                InputStream inputStream = contentResolver.openInputStream(resultUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                String bitmapStr = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                //SharedPreferences pref = getSharedPreferences("save_thumb", Context.MODE_PRIVATE);
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("save_thumb", bitmapStr);
                editor.commit();
                user_thumb.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }
    }

}
