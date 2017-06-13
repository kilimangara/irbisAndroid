package com.nikitazlain.uir.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.persistance.SharedPreferencesWork;
import com.nikitazlain.uir.ui.customviews.CustomToolbar;
import com.nikitazlain.uir.ui.fragments.RubricatorFragment;
import com.nikitazlain.uir.ui.fragments.SearchFragment;
import com.nikitazlain.uir.ui.fragments.SearchProtocolsFragment;
import com.nikitazlain.uir.ui.fragments.ThesurusFragment;
import com.nikitazlain.uir.utils.RxWidgets;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Toolbar.OnMenuItemClickListener {

    public static final int SEARCH_FRAGMENT =0;

    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.main_toolbar)
    CustomToolbar toolbar;

    SearchFragment mainFragment;

    ThesurusFragment thesurusFragment;

    FragmentManager fragmentManager;

    private int currFragment;

    private MenuItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        mainFragment = new SearchFragment();
        fragmentManager = getSupportFragmentManager();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView username = ButterKnife.findById(navigationView.getHeaderView(0), R.id.user_name_header);
        username.setText(SharedPreferencesWork.getInstance().getName());
        switchToSearchFragment();
        toolbar.setTitle("Поиск");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSearchFragment();
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mainFragment.getPresenter().resetPresenter();
                return false;
            }
        });
        toolbar.setMenu(R.menu.navigation);
        toolbar.setOnMenuItemClickListener(this);
    }

    public Observable<String> getQueryObservable(){
        return RxWidgets.fromSearchView(searchView);
    }

    public Observable<Boolean> getClickItemObservable(){
        return RxWidgets.fromMenu(toolbar.getMenu(), R.id.evristik_search);
    }

    private void switchToSearchFragment(){
        currFragment =SEARCH_FRAGMENT;
        fragmentManager.beginTransaction().replace(R.id.nav_frame, mainFragment).commit();
        if(currentItem != null){
            currentItem.setChecked(false);
        }
        currentItem = null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(currentItem!=null) {
                switch (currentItem.getItemId()) {
                    case R.id.nav_thesaurus:
                        if(thesurusFragment!=null) {
                            thesurusFragment.backBehavior(false);
                        }
                        break;
                }
            } else {
                super.onBackPressed();
            }
        }

    }

    public void replaceFragmentWithBundle(Fragment fragment, @Nullable Bundle args){
        if(args!=null){
            fragment.setArguments(args);
        }
        fragmentManager.beginTransaction().replace(R.id.nav_frame, fragment).commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment ;
        currentItem = item;
        searchView.onActionViewCollapsed();
        switch (id){
            case R.id.nav_thesaurus:
                if(thesurusFragment == null){
                    thesurusFragment = new ThesurusFragment();
                }
                fragment = thesurusFragment;
                break;
            case R.id.nav_rubric:
                fragment = new RubricatorFragment();
                break;
            case R.id.nav_previous:
                fragment =new SearchProtocolsFragment();
                break;
            default:
                fragment = mainFragment;
        }
        replaceFragmentWithBundle(fragment, null);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Intent startSettings = new Intent(NavigationActivity.this, SettingsActivity.class);
                startActivity(startSettings);
                return true;
        }
        return false;
    }
}
