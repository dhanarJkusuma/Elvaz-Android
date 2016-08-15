package id.telkom.elvaz.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by VlovaverA on 06/03/2016.
 */
public class SettingUpManager
{
    SharedPreferences __pref;
    SharedPreferences.Editor __editor;
    Context __context;

    private static final String KEY_PREF_NAME = "elvaz";

    private static final String KEY_USER_ADMIN = "user_admin";
    private static final String KEY_USER_PASSWORD = "user_password";




    int PRIVATE_MODE = 0;


    public SettingUpManager(Context context)
    {
        __context = context;
        __pref = __context.getSharedPreferences(KEY_PREF_NAME,PRIVATE_MODE);
        __editor = __pref.edit();
    }

    public void setUsername(String username)
    {
        __editor.putString(KEY_USER_ADMIN,username);__editor.commit();
    }

    public void setPassword(String password)
    {
        __editor.putString(KEY_USER_PASSWORD,password);__editor.commit();
    }

    public String getUsername()
    {
        return __pref.getString(KEY_USER_ADMIN,"admin");
    }

    public String getPassword()
    {
        return __pref.getString(KEY_USER_PASSWORD,"admin");
    }


}
