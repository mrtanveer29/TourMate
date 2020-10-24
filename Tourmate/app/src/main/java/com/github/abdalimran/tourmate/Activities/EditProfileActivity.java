package com.github.abdalimran.tourmate.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.github.abdalimran.tourmate.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_MAINACTIVITY = 2;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @BindView(R.id.edt_name) EditText name;
    @BindView(R.id.edt_email) EditText email;
    @BindView(R.id.edt_password) EditText password;
    @BindView(R.id.edt_mobileno) EditText mobileno;
    @BindView(R.id.edt_country) AutoCompleteTextView country;
    @BindView(R.id.edt_profile_image) de.hdodenhof.circleimageview.CircleImageView edt_profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        ButterKnife.bind(this);
        setResult(RESULT_OK, getIntent());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("TourmateData", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String[] countries = getResources().getStringArray(R.array.countries);

        country.setThreshold(1);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.select_dialog_item,countries);
        country.setAdapter(adapter);

        setEditFields();
    }

    private void setEditFields(){
        if(!sharedPreferences.getString("image", "").equals("")) {
            Bitmap imageB = decodeToBase64(sharedPreferences.getString("image", ""));
            edt_profile_image.setImageBitmap(imageB);
        }
        if(!sharedPreferences.getString("name", "").equals("")){
            name.setText(sharedPreferences.getString("name", ""));
        }
        if(!sharedPreferences.getString("email", "").equals("")) {
            email.setText(sharedPreferences.getString("email", ""));
        }
        if(!sharedPreferences.getString("password", "").equals("")){
            password.setText(sharedPreferences.getString("password", ""));
        }
        if(!sharedPreferences.getString("mobileno", "").equals("")){
            mobileno.setText(sharedPreferences.getString("mobileno", ""));
        }
        if(!sharedPreferences.getString("country", "").equals("")){
            country.setText(sharedPreferences.getString("country", ""));
        }
    }

    @OnClick(R.id.edt_profile_btn)
    public void finishEditProfile(View view){
        editProfile();
    }

    @OnClick(R.id.edt_profile_image)
    public void addProfileImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MAINACTIVITY) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST) {
            if(resultCode == RESULT_OK && data!= null) {
                try {
                    InputStream stream = getContentResolver().openInputStream(data.getData());
                    Bitmap realImage = BitmapFactory.decodeStream(stream);
                    edt_profile_image.setImageBitmap(realImage);
                    String encodedImage=encodeToBase64(realImage);
                    editor.putString("image", encodedImage);
                    Intent intent = new Intent(this, EditProfileActivity.class);
                    startActivity(intent);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0,0);
    }

    public void editProfile() {
        if (validate()) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Updating Profile...");
            progressDialog.show();

            String getname = name.getText().toString();
            String getemail = email.getText().toString();
            String getpassword = password.getText().toString();
            String getmobileno = mobileno.getText().toString();
            String getcountry = country.getText().toString();

            editor.putString("name",getname);
            editor.putString("email",getemail);
            editor.putString("password",getpassword);
            editor.putString("mobileno",getmobileno);
            editor.putString("country",getcountry);
            editor.putBoolean("autologin",true);
            editor.commit();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onUpdateSuccess();
                            progressDialog.dismiss();
                        }
                    }, 2000);
        }
        else {
            onUpdateFailed();
        }
    }

    public void onUpdateSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, REQUEST_MAINACTIVITY);
        finish();
    }

    public void onUpdateFailed() {
        Toast.makeText(getApplicationContext(), "Enter valid information.", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String getname = name.getText().toString();
        String getemail = email.getText().toString();
        String getpassword = password.getText().toString();
        String getmobileno = mobileno.getText().toString();
        String getcountry = country.getText().toString();

        if (getname.isEmpty() || getname.length() < 3) {
            name.setError("At least 3 characters.");
            valid = false;
        }
        else {
            name.setError(null);
        }

        if (getemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(getemail).matches()) {
            email.setError("Enter a valid email address.");
            valid = false;
        } else {
            email.setError(null);
        }

        if (getpassword.isEmpty() || getpassword.length() < 4 || getpassword.length() > 10) {
            password.setError("Between 4 to 10 alphanumeric characters.");
            valid = false;
        } else {
            password.setError(null);
        }

        if (getmobileno.isEmpty() || getmobileno.length() < 11 || getmobileno.length() > 15) {
            mobileno.setError("Between 11 to 15 numbers.");
            valid = false;
        } else {
            mobileno.setError(null);
        }

        if (getcountry.isEmpty()) {
            country.setError("Enter valid country name.");
            valid = false;
        } else {
            country.setError(null);
        }

        return valid;
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
