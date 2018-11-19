package com.example.android.worde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.example.android.worde.MenuClick;
import com.example.android.worde.NightModePreferences;
import com.example.android.worde.R;
import com.example.android.worde.ui.favourite.FavouritesActivity;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    MenuClick menuClick;
    FrameLayout frameLayout;
    SwitchCompat drawerNightModeSwitch;
    NightModePreferences nightModePreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        nightModePreference = new NightModePreferences(getApplicationContext());
        if (nightModePreference.loadNightModeState()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        menuClick = new MenuClick(getApplicationContext());

        //Toolbar toolbar = findViewById(R.id.toolbar2);
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        //setSupportActionBar(toolbar);
        frameLayout= findViewById(R.id.content_frame);

       //getSupportActionBar().setHomeAsUpIndicator(R.drawable.wordelogosmalltransparent);
        // getSupportActionBar().setLogo(R.drawable.ic_launcher_background);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = findViewById(R.id.menu_drawer);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view_base);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity (new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        drawerNightModeSwitch = (SwitchCompat) navigationView.getMenu()
                .findItem(R.id.nav_night_mode).getActionView();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            drawerNightModeSwitch.setChecked(true);

        drawerNightModeSwitch.setOnCheckedChangeListener
                (new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    nightModePreference.setNightModeState(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    applyDayNightMode();
                } else {
                    nightModePreference.setNightModeState(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    applyDayNightMode();
                }
            }
        });
    }
    public void showInfoDialog() {
        DialogFragment infoFragment = new InfoDialogFragment();
        infoFragment.show(getSupportFragmentManager(), "info");
    }

    public void applyDayNightMode(){
        mDrawerLayout.closeDrawer(GravityCompat.START);
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
        overridePendingTransition(0, 0);
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
                menuClick.launchWordListActivity("a1");
                break;
            case R.id.nav_level_a2:
                menuClick.launchWordListActivity("a2");
                break;
            case R.id.nav_level_b1:
                menuClick.launchWordListActivity("b1");
                break;
            case R.id.nav_user_favourites:
                startActivity (new Intent(this, FavouritesActivity.class));
                break;
            case R.id.nav_night_mode:
                return false;
            case R.id.nav_app_info:
                showInfoDialog();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
