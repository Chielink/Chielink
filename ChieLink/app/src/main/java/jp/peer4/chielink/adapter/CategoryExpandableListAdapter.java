package jp.peer4.chielink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import jp.peer4.chielink.R;

/**
 * カテゴリExpandableListViewアダプタークラス
 */
public class CategoryExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context = null;
    private LayoutInflater layoutInflater;
    private List<String> groups;
    private List<List<String>> children;

    public CategoryExpandableListAdapter(Context context, List<String> groups, List<List<String>> children){
        this.context = context;
        layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groups = groups;
        this.children = children;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.expandablelistview_parentcategory, null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.parentCategoryName);
        textView.setText(groups.get(groupPosition));

        ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.favoriteButton);
        imageButton.setImageResource(R.drawable.favorite_32_false);
        imageButton.setFocusable(false);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((ImageButton)v).setImageResource(R.drawable.favorite_32_true);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.expandablelistview_childcategory, null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.childCategoryName);
        textView.setText(children.get(groupPosition).get(childPosition));

        ImageButton imageButton = (ImageButton)convertView.findViewById(R.id.favoriteButton);
        imageButton.setImageResource(R.drawable.favorite_32_false);
        imageButton.setFocusable(false);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageButton)v).setImageResource(R.drawable.favorite_32_true);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
