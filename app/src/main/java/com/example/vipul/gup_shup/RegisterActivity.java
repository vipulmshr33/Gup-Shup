package com.example.vipul.gup_shup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextFullName;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextMobileNumber;

    private Button buttonRegister;

    private static final String REGISTER_URL = "http://vipulmshr.16mb.com/gupshup/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextFullName = (EditText) findViewById(R.id.fullnameRegisterEditText);
        editTextUserName = (EditText) findViewById(R.id.usernameRegisterEditText);
        editTextPassword = (EditText) findViewById(R.id.passwordRegisterEditText);
        editTextEmail = (EditText) findViewById(R.id.emailRegisterEditText);
        editTextMobileNumber = (EditText) findViewById(R.id.mobilenumberRegisterEditText);

        buttonRegister = (Button) findViewById(R.id.registerRegisterButton);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String FullName = editTextFullName.getText().toString().trim().toUpperCase();
        String UserName = editTextUserName.getText().toString().trim().toLowerCase();
        String Password = editTextPassword.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim().toLowerCase();
        String MobileNumber = editTextMobileNumber.getText().toString().trim();

        register(FullName, UserName, Password, Email, MobileNumber);
    }

    private void register(String FullName, String UserName, String Password, String Email, String MobileNumber) {
        class RegisterUser extends AsyncTask<String, Void, String>{

            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast stat = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                stat.show();

                String response = ((TextView)((LinearLayout)stat.getView()).getChildAt(0)).getText().toString().toLowerCase();

                if(response.equals("success")) {
                    Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<String ,String>();
                data.put("FullName",params[0]);
                data.put("UserName",params[1]);
                data.put("Password",params[2]);
                data.put("Email",params[3]);
                data.put("MobileNumber",params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return result;
            }
        }
        RegisterUser reg = new RegisterUser();
        reg.execute(FullName,UserName,Password,Email,MobileNumber);
    }
}
