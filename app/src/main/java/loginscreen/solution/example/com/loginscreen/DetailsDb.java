package loginscreen.solution.example.com.loginscreen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aakansha on 12/21/15.
 */
public class DetailsDb {
    private static final String TAG=DetailsDb.class.getSimpleName();
    private SQLiteDatabase database;
    private DbHelper dbhelper;
    private Context context;
    public static final String USERNAME="username";
    public static final String EMAIL="email";
    public static final String PASSWORD="password";
    public static final String PHONE="phone";
    public DetailsDb(Context context)
    {
        this.context=context;
        dbhelper=new DbHelper();
    }
    public void insert(ContentValues values){
        try {
            database = dbhelper.getWritableDatabase();
            database.insertOrThrow(dbhelper.TABLE_NAME, null, values);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //releasing the resources.
            database.close();
            dbhelper.close();
        }
    }
    public Cursor query(){
        database=dbhelper.getReadableDatabase();
        Cursor cur=database.query(DbHelper.TABLE_NAME, null, null, null, null, null, null);

        return cur;

    }
    public Cursor validate(String email,String password)
    {

        database=dbhelper.getReadableDatabase();
        String sql="select * from details where email= ? and password=?";
        String arr[]={email,password};
        Cursor cur=database.rawQuery(sql,arr);
        return cur;
    }
    private class DbHelper extends SQLiteOpenHelper{
        public  static final String DB_NAME="info.dB";
        private static final int DB_VERSION=1;
        private static final String TABLE_NAME="details";
        public DbHelper() {
            super(context,DB_NAME,null, DB_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql=String.format("create table %s(%s text ,%s text primary key,%s text,%s int)",TABLE_NAME,USERNAME,EMAIL,PASSWORD,PHONE);
            db.execSQL(sql);



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists"+TABLE_NAME);
            this.onCreate(db);

        }
    }
}
