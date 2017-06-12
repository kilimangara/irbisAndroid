package com.nikitazlain.uir.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.persistance.SharedPreferencesWork;
import com.nikitazlain.uir.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginFragment extends Fragment {

    @BindView(R.id.edt_username)
    EditText edtUsername;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @BindView(R.id.button_login)
    Button btnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        Button loginButton =  ButterKnife.findById(view, R.id.button_login);
        loginButton.setOnClickListener((LoginActivity)getActivity());
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public boolean validateForm(){
        boolean withErrors =false;
        if(edtUsername.getText().length() == 0){
            edtUsername.setError(getActivity().getResources().getString(R.string.error_empty_field),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }
        if(edtPassword.getText().length() == 0){
            edtPassword.setError(getActivity().getResources().getString(R.string.error_empty_field),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }
        return !withErrors;
    }

    public boolean proceedLogin(){
        if(!validateForm()){
            return false;
        } else {
            SharedPreferencesWork.getInstance().saveToken("random");
            SharedPreferencesWork.getInstance().saveName(edtUsername.getText().toString());
            return true;
        }
    }
}
