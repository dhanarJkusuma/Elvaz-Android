package id.telkom.elvaz.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.telkom.elvaz.R;
import id.telkom.elvaz.presenter.SettingPresenter;
import id.telkom.elvaz.ui.ui_interface.IUserSetting;
import id.telkom.elvaz.util.Validation;

public class SettingActivity extends AppCompatActivity implements IUserSetting,View.OnClickListener {

    @Bind(R.id.iUsername)
    EditText iUsername;
    @Bind(R.id.iPassword)
    EditText iPassword;
    @Bind(R.id.iPasswordConfirm)
    EditText iPasswordConfirm;

    @Bind(R.id.lUsername)
    TextInputLayout lUsername;
    @Bind(R.id.lPassword)
    TextInputLayout lPassword;
    @Bind(R.id.lPasswordConfirm)
    TextInputLayout lPasswordConfirm;

    @Bind(R.id.btnSave)
    Button btnSave;

    private SettingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("User Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        presenter = new SettingPresenter(this);
        btnSave.setOnClickListener(this);
    }


    private boolean getValidInput()
    {
        return Validation.getValidNormalText(iUsername)&&Validation.getValidNormalText(iPassword)&&Validation.getValidNormalText(iPasswordConfirm);
    }

    private boolean getValidPassword()
    {
        return (iPassword.getText().toString().equals(iPasswordConfirm.getText().toString())) ? true : false;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSave :
                if(getValidInput()&&getValidPassword())
                {
                    presenter.saveConfiguration(iUsername.getText().toString(),iPassword.getText().toString());
                }
                else
                {
                    setToast("Invalid Input");
                }
                break;
        }
    }

    @Override
    public void setToast(String toast) {
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getRelatedContext() {
        return this;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
