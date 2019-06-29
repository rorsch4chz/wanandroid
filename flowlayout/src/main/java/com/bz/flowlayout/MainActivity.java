package com.bz.flowlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void flowLayout(View view) {
        startActivity(new Intent(this, FlowLayoutActivity.class));
    }

    public void flexboxLayout(View view) {
    }

    public void flexboxLayoutManager(View view) {
    }

    public void TagFlowLayout(View view) {
        startActivity(new Intent(this, TagFlowLayoutActivity.class));
    }
}
