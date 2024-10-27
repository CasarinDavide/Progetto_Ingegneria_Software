package com.example.progetto_ingegneria_software.data.model;

import android.app.Activity;
import android.widget.Toast;

public class System {

    public final static int REQUEST_SUCCESSFUL = 200;
    public final static int BAD_REQUEST = 404;
    public final static int INPUT_ERROR = 100;


    public static boolean responseCodeResults(Activity activity, int response_code)
    {
        switch (response_code)
        {
            case REQUEST_SUCCESSFUL:
            {
                return true;
            }

            case BAD_REQUEST:
            {
                Toast error = Toast.makeText(activity.getApplicationContext(),"Qualcosa è andato storto",Toast.LENGTH_LONG);
                error.show();
                break;
            }

            case INPUT_ERROR:
            {
                Toast error = Toast.makeText(activity.getApplicationContext(),"Parametri non validi.",Toast.LENGTH_LONG);
                error.show();
                break;
            }

            default:


        }

        return false;
    }




}
