package id.telkom.elvaz.presenter;

import id.telkom.elvaz.ui.ui_interface.IUserSetting;
import id.telkom.elvaz.util.SettingUpManager;

/**
 * Created by VlovaverA on 30/03/2016.
 */
public class SettingPresenter
{
    private SettingUpManager manager;
    private IUserSetting iUserSetting;
    public SettingPresenter(IUserSetting iUserSetting)
    {
        this.iUserSetting = iUserSetting;
        this.manager = new SettingUpManager(iUserSetting.getRelatedContext());
    }

    public void saveConfiguration(String username,String password)
    {
        manager.setUsername(username);
        manager.setPassword(password);
        iUserSetting.setToast("Configuration Saved.");
    }
}
