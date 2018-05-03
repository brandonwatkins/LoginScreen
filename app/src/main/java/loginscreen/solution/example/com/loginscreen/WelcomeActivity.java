package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

  private TextView mNameTv;
  private TextView mEmailTv;
  private TextView mPhoneTv;

  @Override
  public void onCreate(Bundle instanceState) {
    super.onCreate(instanceState);
    setContentView(R.layout.content_welcome);

    // Retrieve users email that was bundled into the intent passed in
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();

    mNameTv = (TextView) findViewById(R.id.tv_name);
    mEmailTv = (TextView) findViewById(R.id.tv_email);
    mPhoneTv = (TextView) findViewById(R.id.tv_phone);

    String name = "";
    String email = "";
    String phone = "";

    if (bundle != null) {
        email = (String) bundle.get("USEREMAIL");
    }

    DetailsDb db = new DetailsDb(this);

    // Fill cursor with users information
    Cursor cursor = db.query(email);

    // Search cursor for information needed to fill TextVIews
    if (cursor != null) {
        try {
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex("username"));
                phone = cursor.getString(cursor.getColumnIndex("phone"));
            }
        } finally {
            cursor.close();
        }
    }

    mNameTv.setText(name);
    mEmailTv.setText(email);
    mPhoneTv.setText(phone);

  }

}
