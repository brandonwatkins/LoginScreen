package loginscreen.solution.example.com.loginscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


  private Button mLoginBtn;
  private Button mSignUpBtn;
  private Button mSignInBtn;
  private Button mCreateAccountBtn;
  private EditText mNameEt;
  private EditText mPhoneEt;
  private EditText mEmailEt;
  private EditText mPasswordEt;
  private ViewFlipper mViewFlipper;
  private LinearLayout mNameLl;

  // Flag to keep track of what page the user is on
  Boolean onLoginPage;

  @Override
  public void onCreate(Bundle instanceState) {
    super.onCreate(instanceState);
    setContentView(R.layout.content_main);

    mNameEt = (EditText) findViewById(R.id.et_name);
    mPasswordEt = (EditText) findViewById(R.id.et_phone);
    mEmailEt = (EditText) findViewById(R.id.et_email);
    mPasswordEt = (EditText) findViewById(R.id.et_password);
    mSignInBtn = (Button) findViewById(R.id.bt_sign_in);
    mCreateAccountBtn = (Button) findViewById(R.id.bt_create);

    mViewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
    mLoginBtn = (Button) findViewById(R.id.bt_login);
    mSignUpBtn = (Button) findViewById(R.id.bt_signup);

    mNameLl = (LinearLayout) findViewById(R.id.lt_name);

    // Set the flag to true because the login page is displayed first
    onLoginPage = true;

    /**
     * OnClickListeners for the viewPager
     */
    mLoginBtn.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        mViewFlipper.setInAnimation(MainActivity.this, android.R.anim.slide_out_right);
        mViewFlipper.setOutAnimation(MainActivity.this, android.R.anim.slide_in_left);

        // Checks to see what page is currently showing. If login page is already showing
        // it does not showNext();
        if (onLoginPage == false) {
          mViewFlipper.showNext();
          mNameLl.setVisibility(View.INVISIBLE);
          onLoginPage = true;
        }
      }
    });

    mSignUpBtn.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        mViewFlipper.setInAnimation(MainActivity.this, android.R.anim.slide_in_left);
        mViewFlipper.setOutAnimation(MainActivity.this, android.R.anim.slide_out_right);

        // Checks to see what page is currently showing. If sign up is already showing
        // it does not showPrevious();
        if (onLoginPage == true) {
          mViewFlipper.showPrevious();
          mNameLl.setVisibility(View.VISIBLE);
          onLoginPage = false;
        }
      }
    });

  }

  /**
   * Button that takes the user to the WelcomeActivity if the password and email
   * entered is correct.
   * @param view
   */
  public void SignInBtn(View view) {

    Intent i = new Intent(this, WelcomeActivity.class);

    String email = mEmailEt.getText().toString();
    String pass = mPasswordEt.getText().toString();

    CheckCredentials credentials = new CheckCredentials(this);

    // Check if credentials match a db entry.
    if (credentials.isauthenticate(email, pass) == true) {
      startActivity(i);
      // Else display an error message
    } else {
      Toast toast = Toast.makeText(getApplicationContext(), R.string.loginfail, Toast.LENGTH_LONG);
      toast.show();
    }

  }


  /**
   * Button that takes the information out of the EdiText fields. Checks if the information is valid,
   * and then creates a new account.
   * @param view
   */
  public void CreateAccountBtn(View view) {

    Intent i = new Intent(this, WelcomeActivity.class);

    String name = mNameEt.getText().toString();
    String email = mEmailEt.getText().toString();
    String pass = mPasswordEt.getText().toString();
    String phone = mPhoneEt.getText().toString();

    Boolean validInfo = true;


    if (!name.matches("[A-Za-z0-9]+")) {
      mNameEt.setError(R.string.nameError + "");
      validInfo = false;
    }

    if (!isEmailValid(email)) {
      mEmailEt.setError(R.string.emailError + "");
      validInfo = false;
    }

    if (!isPasswordValid(pass)) {
      mPasswordEt.setError(R.string.passwordError + "");
      validInfo = false;
    }

    if (!isPhoneNumberValid(phone)) {
      mPhoneEt.setError(R.string.phoneNumError + "");
      validInfo = false;
    }

    if (validInfo == true) {

      startActivity(i);
    }

  }

  public boolean isEmailValid(CharSequence email) {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
  }

  public boolean isPasswordValid(String password) {

    Pattern pattern;
    Matcher matcher;
    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
    pattern = Pattern.compile(PASSWORD_PATTERN);
    matcher = pattern.matcher(password);

    return matcher.matches();

  }

  public boolean isPhoneNumberValid(String phoneNumber) {

    boolean isNum = TextUtils.isDigitsOnly(phoneNumber);
    int length = phoneNumber.length();

    if(isNum) {
      if (length == 10) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }

  }


}
