package com.bz.flowlayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    private Button button;

    private AppCompatButton appCompatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        appCompatButton = findViewById(R.id.appcompat_button);

        AppCompatButton button1 = new AppCompatButton(this);

        Context buttonContext = button.getContext();
        Context appCompatButtonContext = appCompatButton.getContext();

        Context button1Context = button1.getContext();

    }

    public void flowLayout(View view) {
        startActivity(new Intent(this, FlowLayoutActivity.class));
    }

    public void flexboxLayout(View view) {
    }

    public void flexboxLayoutManager(View view) {
    }
}
