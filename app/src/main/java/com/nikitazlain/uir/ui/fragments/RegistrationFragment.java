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


public class RegistrationFragment extends Fragment {

    @BindView(R.id.edt_username_registration)
    EditText edtUsername;

    @BindView(R.id.edt_password_registration)
    EditText edtPassword;

    @BindView(R.id.edt_reenter_password_registration)
    EditText edtRepeatPassword;

    @BindView(R.id.edt_email_registration)
    EditText edtEmail;

    @BindView(R.id.edt_fio_registration)
    EditText edtFIO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        ButterKnife.bind(this, view);
        Button registrationButton = ButterKnife.findById(view, R.id.button_register);
        registrationButton.setOnClickListener((LoginActivity)getActivity());
        return view;
    }

    public boolean validateForm(){
        boolean withErrors = false;
        if(edtUsername.getText().length() == 0) {
            edtUsername.setError(getActivity().getResources().getString(R.string.error_empty_field),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }
        if(edtPassword.getText().length() == 0) {
            edtPassword.setError(getActivity().getResources().getString(R.string.error_empty_field),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }
        if(edtRepeatPassword.getText().length() == 0) {
            edtRepeatPassword.setError(getActivity().getResources().getString(R.string.error_empty_field),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }
        if(edtEmail.getText().length() == 0) {
            edtEmail.setError(getActivity().getResources().getString(R.string.error_empty_field),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }
        if(edtFIO.getText().length() == 0) {
            edtFIO.setError(getActivity().getResources().getString(R.string.error_empty_field),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }
        if(!edtRepeatPassword.getText().toString().equals(edtPassword.getText().toString())){
            edtRepeatPassword.setError(getActivity().getResources().getString(R.string.error_password_not_equal),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            edtPassword.setError(getActivity().getResources().getString(R.string.error_password_not_equal),
                    ContextCompat.getDrawable(getActivity(), R.drawable.error_outline_red_24x24));
            withErrors = true;
        }

        return !withErrors;
    }

    public boolean proceedRegistration(){
        if(!validateForm()){
            return false;
        } else {
            SharedPreferencesWork.getInstance().saveToken("random");
            SharedPreferencesWork.getInstance().saveName(edtUsername.getText().toString());
            return true;
        }
    }


}
