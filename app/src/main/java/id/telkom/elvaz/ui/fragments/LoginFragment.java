package id.telkom.elvaz.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.telkom.elvaz.R;
import id.telkom.elvaz.presenter.LoginPresenter;
import id.telkom.elvaz.ui.activity.LocationActivity;
import id.telkom.elvaz.ui.ui_interface.IUserLogin;
import id.telkom.elvaz.util.Validation;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements IUserLogin, View.OnClickListener, View.OnFocusChangeListener {

    @Bind(R.id.iUsername)
    EditText iUsername;
    @Bind(R.id.iPassword)
    EditText iPassword;

    @Bind(R.id.lUsername)
    TextInputLayout lUsername;
    @Bind(R.id.lPassword)
    TextInputLayout lPassword;

    @Bind(R.id.bLogin)
    Button bLogin;

    private LoginPresenter presenter;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,rootView);
        presenter = new LoginPresenter(this);
        bLogin.setOnClickListener(this);

        iUsername.setOnFocusChangeListener(this);
        iPassword.setOnFocusChangeListener(this);

        return rootView;
    }

    @Override
    public void nextLogin()
    {
        Intent intent = new Intent(getActivity(), LocationActivity.class);
        startActivity(intent);
    }

    @Override
    public void setLoginInvalid()
    {
        Toast.makeText(getContext(),"Invalid User",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setToast(String toast)
    {
        Toast.makeText(getContext(),toast,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getRelatedContext() {
        return getContext();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.bLogin:
                if(Validation.getValidNormalText(iUsername)&&Validation.getValidNormalText(iPassword))
                {
                    presenter.signIn(iUsername.getText().toString(),iPassword.getText().toString());
                }
                else
                {
                    setToast("Field cannot be empty");
                }
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId())
        {
            case R.id.iUsername:
                if(!hasFocus) Validation.NormalTextValidation(iUsername,"Username",lUsername);
                break;
            case R.id.iPassword:
                if(!hasFocus) Validation.NormalTextValidation(iPassword,"Password",lPassword);
                break;
        }
    }
}
