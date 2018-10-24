package com.example.android.worde.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.example.android.worde.R;
import com.example.android.worde.database.WordViewModel;
import com.example.android.worde.ui.list.WordListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    CardView a1LevelButton;
    CardView a2LevelButton;
    CardView b1LevelButton;
    String wordLevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.menu_drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        WordViewModel mViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        a1LevelButton = (CardView) findViewById(R.id.level_a1_cardview);
        a2LevelButton = (CardView) findViewById(R.id.level_a2_cardview);
        b1LevelButton = (CardView) findViewById(R.id.level_b1_cardview);

        a1LevelButton.setOnClickListener(this::onClick);
        a2LevelButton.setOnClickListener(this::onClick);
        b1LevelButton.setOnClickListener(this::onClick);
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
            default:
                break;
        }
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_level_a1) {
            onClick(a1LevelButton);
        } else if (id == R.id.nav_level_a2) {
            onClick(a2LevelButton);
        } else if (id == R.id.nav_level_b1) {
            onClick(b1LevelButton);
        } else if (id == R.id.nav_user_favourites) {

        } else if (id == R.id.nav_app_info){
            showInfoDialog();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.menu_drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
