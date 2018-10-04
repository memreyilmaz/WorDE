package com.example.android.worde;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.level_a1_button) Button a1LevelButton;
    @BindView(R.id.level_a2_button) Button a2LevelButton;
    @BindView(R.id.level_b1_button) Button b1LevelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        a1LevelButton.setOnClickListener(this);
        a2LevelButton.setOnClickListener(this);
        b1LevelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, WordListActivity.class);
                /*mCurrentRecipe = recipe;
                Bundle bundle = new Bundle();

                bundle.putParcelable(DETAIL_KEY, mCurrentRecipe);

                intent.putExtras(bundle);*/
        startActivity(intent);

        /*switch (v.getId()) {

            case R.id.level_a1_button:
                // do your code
                break;

            case R.id.level_a2_button:
                // do your code
                break;

            case R.id.level_b1_button:
                // do your code
                break;

            default:
                break;
        }*/
    }
}
