package com.example.android.worde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.level_a1_button) Button a1LevelButton;
    @BindView(R.id.level_a2_button) Button a2LevelButton;
    @BindView(R.id.level_b1_button) Button b1LevelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
