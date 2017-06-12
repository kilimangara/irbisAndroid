package com.nikitazlain.uir.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.providers.StaticClasses;
import com.nikitazlain.uir.ui.adapter.LoginViewPagerAdapter;
import com.nikitazlain.uir.ui.fragments.LoginFragment;
import com.nikitazlain.uir.ui.fragments.RegistrationFragment;
import com.nikitazlain.uir.ui.customviews.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.main_toolbar)
    CustomToolbar toolbar;

    @BindView(R.id.login_tab)
    TabLayout tabLayout;

    @BindView(R.id.login_view_pager)
    ViewPager viewPager;

    LoginViewPagerAdapter adapter;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        setUi();
        toolbar.setTitle(getString(R.string.login));
        toolbar.setNavigationOnClickListener(StaticClasses.EMPTY_LISTENER);
    }

    public void setUi(){
        adapter = new LoginViewPagerAdapter(fragmentManager);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.login)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.registration)));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.button_login:
                 LoginFragment loginFragment = (LoginFragment) adapter.getItem(LoginViewPagerAdapter.FRAGMENT_LOGIN);
                 if(loginFragment.proceedLogin()){
                     finish();
                 }
                 break;
             case R.id.button_register:
                 RegistrationFragment registrationFragment = (RegistrationFragment) adapter.getItem(LoginViewPagerAdapter.FRAGMENT_REGISTER);
                 if(registrationFragment.proceedRegistration()){
                     finish();
                 }
                 break;
         }
    }
}
