package loginscreen.solution.example.com.loginscreen;

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

    mNameTv = (TextView) findViewById(R.id.tv_name);
    mEmailTv = (TextView) findViewById(R.id.tv_email);
    mPhoneTv = (TextView) findViewById(R.id.et_phone);

    String name = "";
    String email = "";
    String phone = "";

    DetailsDb db = new DetailsDb(this);

    db.query();

    mNameTv.setText(name);
    mEmailTv.setText(email);
    mPhoneTv.setText(phone);


  }

}
