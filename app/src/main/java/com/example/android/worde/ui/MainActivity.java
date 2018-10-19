package com.example.android.worde.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.android.worde.R;
import com.example.android.worde.database.WordViewModel;
import com.example.android.worde.ui.list.WordListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //@BindView(R.id.level_a1_cardview) CardView a1LevelButton;
    //@BindView(R.id.level_a2_cardview) CardView a2LevelButton;
    //@BindView(R.id.level_b1_cardview) CardView b1LevelButton;
    //WordViewModel mViewModel;
    String wordLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);
        CardView a1LevelButton = (CardView) findViewById(R.id.level_a1_cardview);
        CardView a2LevelButton = (CardView) findViewById(R.id.level_a2_cardview);
        CardView b1LevelButton = (CardView) findViewById(R.id.level_b1_cardview);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.menu_drawer);
        WordViewModel mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        a1LevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a1levelIntent = new Intent(MainActivity.this, WordListActivity.class);
                wordLevel = "a1";
                a1levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
                startActivity(a1levelIntent);
            }
        });
        //a1LevelButton.setOnClickListener(this);
        //a2LevelButton.setOnClickListener(this);
        //b1LevelButton.setOnClickListener(this);
        a2LevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a2levelIntent = new Intent(MainActivity.this, WordListActivity.class);
                wordLevel = "a2";
                a2levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
                startActivity(a2levelIntent);
            }
        });
        b1LevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b1levelIntent = new Intent(MainActivity.this, WordListActivity.class);
                wordLevel = "b1";
                b1levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
                startActivity(b1levelIntent);
            }
        });

   }

    @Override
    public void onClick(View v) {
       /* Intent intent = new Intent(MainActivity.this, WordListActivity.class);

                intent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
                startActivity(intent);

        startActivity(intent);*/

        switch (v.getId()) {

            case R.id.level_a1_cardview:
                //wordLevel = "a1";
                break;

            case R.id.level_a2_cardview:
                //wordLevel = "a2";
                break;

            case R.id.level_b1_cardview:
                //wordLevel = "b1";
                break;

            default:
                break;
        }
    }
}
