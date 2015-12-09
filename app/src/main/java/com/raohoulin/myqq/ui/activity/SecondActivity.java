package com.raohoulin.myqq.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.raohoulin.myqq.R;
import com.raohoulin.myqq.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by Administrator on 2015.12.4.
 */
public class SecondActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.popup)
    Button popup;
    private PopupWindow popupWindow;
    private ListView popupListView;

    private List<Map<String, String>> popupData;
    private int popupItemCount = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SecondActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        popup.setOnClickListener(this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.second_activity;
    }

    @Override
    public void initView() {
        View popupLayout = getLayoutInflater().inflate(R.layout.popup_layout, null);
        popupListView = (ListView) popupLayout.findViewById(R.id.popup_list_view);
        popupWindow = new PopupWindow(popupLayout);
        // 设置PopupWindow中的ListView可以接收点击事件
        popupWindow.setFocusable(true);
        popupListView.setAdapter(new SimpleAdapter(this, popupData, R.layout.popup_layout_list_view_item, new String[]{"key"}, new int[]{R.id.value}));
        popupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SecondActivity.this, popupData.get(position).get("key"), Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });
        // 设置PopupWindow宽高自适应
        popupListView.measure(popupLayout.getMeasuredWidth(), popupLayout.getMeasuredHeight());
        popupWindow.setWidth(popupListView.getMeasuredWidth());
        popupWindow.setHeight(popupListView.getMeasuredHeight() * popupItemCount + 60);
        // 控制PopupWindow点击其他地方不阻塞
        popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.popup_window_bg));
    }

    @Override
    public void initData() {
        popupData = new ArrayList<>();
        Map<String, String> map;
        map = new HashMap<>();
        map.put("key", "ONE");
        popupData.add(map);
        map = new HashMap<>();
        map.put("key", "TWO");
        popupData.add(map);
        map = new HashMap<>();
        map.put("key", "THREE");
        popupData.add(map);
        map = new HashMap<>();
        map.put("key", "FORE");
        popupData.add(map);
        popupItemCount = popupData.size() + 1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup:
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                } else {
                    popupWindow.showAsDropDown(v);
                }
            default:
                break;
        }
    }
}
