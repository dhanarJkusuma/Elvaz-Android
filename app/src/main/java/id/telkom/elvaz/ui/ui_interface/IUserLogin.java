package id.telkom.elvaz.ui.ui_interface;

import android.content.Context;

/**
 * Created by VlovaverA on 06/03/2016.
 */
public interface IUserLogin
{
    void nextLogin();
    void setLoginInvalid();
    void setToast(String toast);
    Context getRelatedContext();
}
