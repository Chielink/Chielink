package jp.peer4.chielink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import jp.peer4.chielink.adapter.CategoryExpandableListAdapter;

public class TabCategoryListFragment extends Fragment {

    // フラグメントレイアウト
    View view;

    // カテゴリリスト
    ExpandableListView expandableList;

    // 親カテゴリリスト
    List<String> groupList;
    // 子カテゴリリスト
    List<List<String>> childList;

    /**
     * コンストラクタ
     */
    public TabCategoryListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // フラグメントレイアウト設定
        view = inflater.inflate(R.layout.fragment_tab_category_list, container, false);

        expandableList = (ExpandableListView)view.findViewById(R.id.expandableListView);

        groupList = new ArrayList<>();
        childList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // Group（親）のリスト
            groupList.add("ParentCategory " + (i + 1));
            // Childのリスト
            List<String> childElements = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                childElements.add("ChildCategory " + (j + 1));
            }
            childList.add(childElements);
        }

        CategoryExpandableListAdapter adapter = new CategoryExpandableListAdapter(getActivity(), groupList, childList);

        expandableList.setAdapter(adapter);
        
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < groupList.size(); i++) {
                    if ((groupPosition != i) && expandableList.isGroupExpanded(i))
                        expandableList.collapseGroup(i);
                }
            }
        });

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), QuestionListActivity.class);
                startActivity(intent);
                return false;
            }
        });

        return view;
    }

}
