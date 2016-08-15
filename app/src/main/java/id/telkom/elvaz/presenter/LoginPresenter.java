package id.telkom.elvaz.presenter;

import id.telkom.elvaz.ui.ui_interface.IUserLogin;
import id.telkom.elvaz.util.SettingUpManager;

/**
 * Created by VlovaverA on 06/03/2016.
 */
public class LoginPresenter
{
    private IUserLogin iLogin;
    private SettingUpManager settingUpManager;
    public LoginPresenter(IUserLogin iLogin)
    {
        this.iLogin = iLogin;
        settingUpManager = new SettingUpManager(this.iLogin.getRelatedContext());
    }

    public void signIn(String username,String password)
    {
        if(username.equals(settingUpManager.getUsername()) && password.equals(settingUpManager.getPassword()))
        {
            iLogin.nextLogin();
        }
        else
        {
            iLogin.setLoginInvalid();
        }
    }
}
