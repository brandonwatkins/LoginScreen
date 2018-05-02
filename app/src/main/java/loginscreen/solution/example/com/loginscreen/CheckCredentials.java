package loginscreen.solution.example.com.loginscreen;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Aakansha on 12/21/15.
 */
public class CheckCredentials {
    Cursor cursor;
    DetailsDb db;
    Context context;
    CheckCredentials(Context context){
        this.context=context;
    }

    public boolean isauthenticate(String email,String pass)        
    {
        db=new DetailsDb(context);
        cursor=db.validate(email,pass);
        if(cursor.getCount()==0)
            return false;
        return true;

    }
}
