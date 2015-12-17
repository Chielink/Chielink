package jp.peer4.chielink;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * リストタブフラグメントクラス
 */
public class TabQuestionListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    // リストタイプ
    private static final String LIST_TYPE = "listType";
    private String listType;

    // フラグメントレイアウト
    private View view;

    // 画面サイズ
    private Point point;

    // ポップアップウィンドウ
    private PopupWindow popupWindow;

    // SwipeRefreshLayout
    private SwipeRefreshLayout swipeRefreshLayout;

    // アダプター
    private ArrayAdapter<String> adapter;

    // テストデータ
    String[] roomData = { "新着部屋1",  "新着部屋2",  "新着部屋3",  "新着部屋4",  "新着部屋5",
                          "新着部屋6",  "新着部屋7",  "新着部屋8",  "新着部屋9",  "新着部屋10",
                          "新着部屋11", "新着部屋12", "新着部屋13", "新着部屋14", "新着部屋15",
                          "新着部屋16", "新着部屋17", "新着部屋18", "新着部屋19", "新着部屋20" };

    /**
     * インスタンス生成
     */
    public static TabQuestionListFragment newInstance(String param) {
        TabQuestionListFragment fragment = new TabQuestionListFragment();
        Bundle args = new Bundle();
        args.putString(LIST_TYPE, param);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * コンストラクタ
     */
    public TabQuestionListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listType = getArguments().getString(LIST_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // フラグメントレイアウト設定
        view = inflater.inflate(R.layout.fragment_tab_question_list, container, false);

        // 画面サイズを取得
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);

        // ポップアップウィンドウ設定
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupwindow_room_info, null);
        popupWindow = new PopupWindow(getActivity());
        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popup_room_info_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(point.x / 5 * 4);
        popupWindow.setHeight(point.y / 5 * 4);

        ImageButton close = (ImageButton) popupView.findViewById(R.id.imageButton);
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }
        });

        Button enter = (Button) popupView.findViewById(R.id.enter);
        enter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
                startActivity(intent);
            }
        });

        // SwipeRefreshLayout設定
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeFrefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.red,
                                                   R.color.green,
                                                   R.color.blue,
                                                   R.color.orange);
        swipeRefreshLayout.setOnRefreshListener(this);

        // リストビュー設定
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, roomData);
        ListView newList = (ListView)view.findViewById(R.id.newList);
        newList.setAdapter(adapter);
        newList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                listItemClick();
            }
        });
        newList.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listItemLongClick();
                return false;
            }
        });

        return view;
    }

    /**
     * リストビュー項目タップ時の処理
     */
    public void listItemClick() {
        // ポップアップウィンドウ表示
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, point.y / 38);
    }

    /**
     * リストビュー項目ロングタップ時の処理
     */
    public void listItemLongClick() {

    }

    /**
     * リストビュー更新時の処理
     */
    @Override
    public void onRefresh() {
        // データ更新
        for(int i = 0; i < roomData.length; i++){
            roomData[i] = "新着部屋" + (Integer.parseInt(roomData[i].substring(4)) + (int)(Math.random() * 10));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // リストビュー更新
                adapter.notifyDataSetChanged();
                // リストビュー更新完了
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
