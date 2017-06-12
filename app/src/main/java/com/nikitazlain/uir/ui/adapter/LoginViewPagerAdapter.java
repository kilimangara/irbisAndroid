package com.nikitazlain.uir.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nikitazlain.uir.ui.fragments.LoginFragment;
import com.nikitazlain.uir.ui.fragments.RegistrationFragment;

/**
 * Created by nikitazlain on 14.05.17.
 */

public class LoginViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final int FRAGMENT_LOGIN = 0;

    public static final int FRAGMENT_REGISTER = 1;

    private LoginFragment loginFragment;

    private RegistrationFragment registrationFragment;

    public LoginViewPagerAdapter(FragmentManager fm) {
        super(fm);
        loginFragment =  new LoginFragment();
        registrationFragment = new RegistrationFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case FRAGMENT_LOGIN:
                return loginFragment;
            case FRAGMENT_REGISTER:
                return registrationFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
