package id.telkom.elvaz.util;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by VlovaverA on 05/03/2016.
 */
public class Validation
{
    public static void NormalTextValidation(EditText editText,String titleForm,int minLength,TextInputLayout inputLayout)
    {
        inputLayout.setError(null);
        editText.setError(null);
        if(editText.getText().toString().isEmpty())
        {
            editText.setError(titleForm + " cannot be empty");
            inputLayout.setError("Invalid");
        }
        else if(editText.getText().toString().length()<minLength)
        {
            editText.setError("Minimal character " + minLength);
            inputLayout.setError("Invalid");
        }
    }

    public static void NormalTextValidation(EditText editText,String titleForm,int minLength)
    {
        editText.setError(null);
        if(editText.getText().toString().isEmpty())
        {
            editText.setError(titleForm + " cannot be empty");
        }
        else if(editText.getText().toString().length()<minLength)
        {
            editText.setError("Minimal character " + minLength);
        }
    }

    public static void NormalTextValidation(EditText editText,String titleForm,TextInputLayout inputLayout)
    {
        inputLayout.setError(null);
        editText.setError(null);
        if(editText.getText().toString().isEmpty())
        {
            editText.setError(titleForm + " cannot be empty");
            inputLayout.setError("invalid");
        }
    }

    public static void NormalTextValidation(EditText editText,String titleForm)
    {
        editText.setError(null);
        if(editText.getText().toString().isEmpty())
        {
            editText.setError(titleForm + " cannot be empty");
        }
    }



    public static boolean getValidNormalText(EditText editText)
    {
        boolean isValid = false;
        if(editText.getText().toString().length() > 0)
        {
            isValid = true;
        }
        return isValid;
    }

    public static void EmailValidation(EditText editText, TextInputLayout inputLayout)
    {
        inputLayout.setError(null);
        if(editText.getText().toString().isEmpty())
        {
            inputLayout.setError("Invalid");
            editText.setError("email address cannot be empty");
        }
        else if(!editText.getText().toString().contains("@"))
        {
            inputLayout.setError("Invalid");
            editText.setError("Invalid email address");
        }
    }

    public static void EmailValidation(EditText editText)
    {
        if(editText.getText().toString().isEmpty())
        {
            editText.setError("email address cannot be empty");
        }
        else if(!editText.getText().toString().contains("@"))
        {
            editText.setError("Invalid email address");
        }
    }

    public static boolean getValidEmail(EditText editText)
    {
        boolean isValid = false;
        if(!editText.getText().toString().isEmpty()&&editText.getText().toString().contains("@"))
        {
            isValid = true;
        }
        return isValid;

    }

    public static String parseComa(String string)
    {
        return string.replace(",",".");
    }

    public static Date parseStringtoDate(String sdate)
    {
        Date date=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            date = dateFormat.parse(sdate);
            System.out.println("Testing : "+dateFormat.format(date));
        }
        catch (ParseException ex)
        {
            System.out.println("Exception "+ex);
        }
        return date;
    }

    public static String generateDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        );
        Date date = new Date();
        return dateFormat.format(date);
    }
}
