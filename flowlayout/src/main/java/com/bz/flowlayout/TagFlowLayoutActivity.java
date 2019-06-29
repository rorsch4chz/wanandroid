package com.bz.flowlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bz.flowlayout.view.TagAdapter;
import com.bz.flowlayout.view.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: 柏洲
 * Email:  baizhoussr@gmail.com
 * Date:   2019-06-29 23:56
 * Desc:
 */
public class TagFlowLayoutActivity extends AppCompatActivity {

    private TagFlowLayout mFlowLayout;

    private static final List<String> tags = new ArrayList<>(Arrays.asList("Arthas is a Java Diagnostic tool open sourced by Alibaba. Arthas allows developers to troubleshoot production issues for Java applications without modifying code or restarting servers.".split(" ")));

    private TagAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_flow_layout);

        mFlowLayout = findViewById(R.id.tag_flow_layout);
        mFlowLayout.setMaxSelectCount(3);

        findViewById(R.id.changeDataButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeData(v);
            }
        });

        mFlowLayout.setAdapter(mAdapter = new TagAdapter() {
            @Override
            public int getItemCount() {
                return tags.size();
            }

            @Override
            public void bindView(View view, int position) {
                TextView tv = view.findViewById(R.id.tag_tv);
                tv.setText(tags.get(position));
            }

            @Override
            public View createView(LayoutInflater inflater, ViewGroup parent, int position) {
                return inflater.inflate(R.layout.item_select_tag, parent, false);
            }

            @Override
            public void tipForSelectMax(View view, int maxSelectCount) {
                Toast.makeText(view.getContext(), "最多选择" + maxSelectCount + "个tag", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemSelected(View view, int position) {
                TextView tv = view.findViewById(R.id.tag_tv);
                tv.setTextColor(Color.RED);
            }

            @Override
            public void onItemUnSelected(View view, int position) {
                TextView tv = view.findViewById(R.id.tag_tv);
                tv.setTextColor(Color.BLACK);
            }
        });

    }

    public void onChangeData(View view) {
        tags.clear();
        tags.addAll(new ArrayList<>(Arrays.asList("FlutterBoost is a Flutter plugin which enables hybrid integration of Flutter for your existing native apps with minimum efforts".split(" "))));
        mFlowLayout.setMaxSelectCount(1);
        mAdapter.notifyDataSetChanged();

    }


    public void getSelectedString(View view) {
        Toast.makeText(this, mFlowLayout.getSelectedViewStr(), Toast.LENGTH_SHORT).show();
    }

}
