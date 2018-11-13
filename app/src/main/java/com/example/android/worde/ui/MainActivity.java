package com.example.android.worde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.android.worde.R;
import com.example.android.worde.ui.favourite.FavouritesActivity;
import com.example.android.worde.ui.list.WordListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    CardView a1LevelButton;
    CardView a2LevelButton;
    CardView b1LevelButton;
    CardView favouriteWordsButton;
    String wordLevel;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.wordelogosmalltransparent);
       // getSupportActionBar().setLogo(R.drawable.ic_launcher_background);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = findViewById(R.id.menu_drawer);

        /*ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();*/
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        a1LevelButton = findViewById(R.id.level_a1_cardview);
        a2LevelButton = findViewById(R.id.level_a2_cardview);
        b1LevelButton = findViewById(R.id.level_b1_cardview);
        favouriteWordsButton = findViewById(R.id.favourite_words_cardview);

        a1LevelButton.setOnClickListener(this::onClick);
        a2LevelButton.setOnClickListener(this::onClick);
        b1LevelButton.setOnClickListener(this::onClick);
        favouriteWordsButton.setOnClickListener(this::onClick);
   }
    public void showInfoDialog() {
        DialogFragment infoFragment = new InfoDialogFragment();
        infoFragment.show(getSupportFragmentManager(), "info");
    }
        @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.level_a1_cardview:
                Intent a1levelIntent = new Intent(MainActivity.this, WordListActivity.class);
                wordLevel = "a1";
                a1levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
                startActivity(a1levelIntent);
                break;
            case R.id.level_a2_cardview:
                Intent a2levelIntent = new Intent(MainActivity.this, WordListActivity.class);
                wordLevel = "a2";
                a2levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
                startActivity(a2levelIntent);
                break;
            case R.id.level_b1_cardview:
                Intent b1levelIntent = new Intent(MainActivity.this, WordListActivity.class);
                wordLevel = "b1";
                b1levelIntent.putExtra(WordListActivity.SELECTED_LEVEL, wordLevel);
                startActivity(b1levelIntent);
                break;
            case R.id.favourite_words_cardview:
                Intent favouriteIntent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(favouriteIntent);
                break;
            default:
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_level_a1:
            onClick(a1LevelButton);
            break;
            case R.id.nav_level_a2:
            onClick(a2LevelButton);
            break;
            case R.id.nav_level_b1:
            onClick(b1LevelButton);
            break;
            case R.id.nav_user_favourites:
            onClick(favouriteWordsButton);
            break;
            case R.id.nav_night_mode:
            //TODO IMPLEMENT NIGHT MODE METHOD
            break;
            case R.id.nav_app_info:
            showInfoDialog();
            break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
