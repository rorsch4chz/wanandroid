package com.bz.flowlayout;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bz.flowlayout.view.FlowLayout;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mFlowLayout = findViewById(R.id.flow_layout);
        mFlowLayout.addView(generateButton());
    }

    private Button generateButton() {
        Button button = new Button(this);
        button.setText("ADD");
//        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return button;
    }

}
