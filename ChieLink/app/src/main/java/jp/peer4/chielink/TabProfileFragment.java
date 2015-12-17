package jp.peer4.chielink;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TabProfileFragment extends Fragment {

    public TabProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_profile, container, false);
        ImageView user_thumb = (ImageView) view.findViewById(R.id.user_thumb);
        TextView textview_nicname = (TextView) view.findViewById(R.id.textview_nicname);
        TextView textview_profile = (TextView) view.findViewById(R.id.textview_profile);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        textview_nicname.setText(pref.getString("save_name", null), TextView.BufferType.NORMAL);
        textview_profile.setText(pref.getString("save_profile", null), TextView.BufferType.NORMAL);

        //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String s = pref.getString("save_thumb", "");
        //String s = "";

        try {
            if (/*preference.getBoolean("Launched", false)!=false || */!s.equals("")) {
                byte[] b = Base64.decode(s, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
                user_thumb.setImageBitmap(bmp);
            } else {
                user_thumb.setImageResource(R.drawable.icon);
                //editor.putBoolean("Launched", true);
                //editor.commit();
                //t_thumb.show();
            }
        } catch (Exception e) {

        }
        return view;
    }

}
