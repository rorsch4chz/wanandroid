package com.bz.flowlayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bz.flowlayout.view.FlowLayout;

import java.util.Arrays;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;

    private static final List<String> tags = Arrays.asList("android", "java", "rn", "ios", "sdk", "oc");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mFlowLayout = findViewById(R.id.flow_layout);
    }

    public void add(View view) {
        TextView tagTv = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tag, mFlowLayout, false);
        tagTv.setText(tags.get((int) (Math.random() * (tags.size() - 1))));
        mFlowLayout.addView(tagTv);
    }

}
