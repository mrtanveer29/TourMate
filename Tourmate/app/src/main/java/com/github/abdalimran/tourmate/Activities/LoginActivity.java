package com.github.abdalimran.tourmate.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.abdalimran.tourmate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 1;
    private static final int REQUEST_LOGIN = 2;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @BindView(R.id.app_title) TextView appTitle;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.signup_tv) TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setResult(RESULT_OK, getIntent());

        sharedPreferences = getSharedPreferences("TourmateData", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        Typeface appTitleFont = Typeface.createFromAsset(getAssets(),"fonts/Impregnable.ttf");
        appTitle.setTypeface(appTitleFont);

        if(sharedPreferences.getBoolean("autologin",false)==true) {
            autologin();
        }
    }

    @OnClick(R.id.login_btn)
    public void performLogin(View view) {
        login();
    }

    @OnClick(R.id.signup_tv)
    public void performSignup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    public void autologin() {
        final String loginemail = sharedPreferences.getString("email", "");
        final String loginpassword = sharedPreferences.getString("password", "");
        if(loginemail!=null && loginpassword!=null) {
            onLoginSuccess();
        }
    }

    public void login() {
        if (validate()) {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            final String getemail = email.getText().toString();
            final String getpassword = password.getText().toString();

            final String loginemail = sharedPreferences.getString("email", "");
            final String loginpassword = sharedPreferences.getString("password", "");

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            if(getemail.equals(loginemail) && getpassword.equals(loginpassword)) {
                                onLoginSuccess();
                                progressDialog.dismiss();
                            }
                            else {
                                onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }
                    }, 2000);
        }
        else {
            Toast.makeText(LoginActivity.this, "Enter valid information.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    @Override
    public void onStart() {
        super.onStart();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }

        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, REQUEST_LOGIN);
        editor.putBoolean("autologin",true);
        editor.commit();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Username/Passwrod incorrect.", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String getemail = email.getText().toString();
        String getpassword = password.getText().toString();

        if (getemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(getemail).matches()) {
            email.setError("Enter a valid email address.");
            valid = false;
        }
        else {
            email.setError(null);
        }

        if (getpassword.isEmpty() || getpassword.length() < 4 || getpassword.length() > 10) {
            password.setError("Between 4 and 10 alphanumeric characters.");
            valid = false;
        }
        else {
            password.setError(null);
        }

        return valid;
    }
}
