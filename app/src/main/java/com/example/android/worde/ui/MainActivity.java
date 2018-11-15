package com.example.android.worde.ui;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.example.android.worde.MenuClick;
import com.example.android.worde.R;

public class MainActivity extends DrawerActivity implements View.OnClickListener{
    CardView a1LevelButton;
    CardView a2LevelButton;
    CardView b1LevelButton;
    CardView favouriteWordsButton;
    MenuClick menuClick;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        frameLayout = findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        menuClick = new MenuClick(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.wordelogosmalltransparent);
       // getSupportActionBar().setLogo(R.drawable.ic_launcher_background);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a1LevelButton = findViewById(R.id.level_a1_cardview);
        a2LevelButton = findViewById(R.id.level_a2_cardview);
        b1LevelButton = findViewById(R.id.level_b1_cardview);
        favouriteWordsButton = findViewById(R.id.favourite_words_cardview);
        a1LevelButton.setOnClickListener(this::onClick);
        a2LevelButton.setOnClickListener(this::onClick);
        b1LevelButton.setOnClickListener(this::onClick);
        favouriteWordsButton.setOnClickListener(this::onClick);
   }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level_a1_cardview:
                menuClick.launchWordListActivity("a1");
                break;
            case R.id.level_a2_cardview:
                menuClick.launchWordListActivity("a2");
                break;
            case R.id.level_b1_cardview:
                menuClick.launchWordListActivity("b1");
                break;
            case R.id.favourite_words_cardview:
              menuClick.launchFavouriteWordsActivity(favouriteWordsButton);
                break;
            default:
                break;
        }
    }
}
