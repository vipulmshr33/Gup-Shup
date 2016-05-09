package com.example.vipul.gup_shup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class UpdateStatusActivity extends AppCompatActivity {

    private EditText editTextStatus;
    private Button buttonUpdate;

    private static final String STATUS_URL = "http://vipulmshr.16mb.com/gupshup/update_status.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        editTextStatus = (EditText) findViewById(R.id.statusUpdateStatusEditText);
        buttonUpdate = (Button) findViewById(R.id.updateUpdateStatusButton);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the current user
                SharedPreferences sharedPreferences = getSharedPreferences(LoginConfig.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                String UserName = sharedPreferences.getString(LoginConfig.EMAIL_SHARED_PREF,"Not Available");

                //get the status user has entered and convert it to string
                String Status = editTextStatus.getText().toString();

                if (Status.isEmpty()) {

                    // Status cannot be empty.. Advise the user.
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
                    builder.setMessage("Status can't be left empty !");
                    builder.setTitle("Error");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // close the dialog
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                // Update the status and save it to the backend
                updateStatus(UserName , Status);

            }
        });

    }

    // Definition for updateStatus function
    private void updateStatus(String UserName , String Status) {
        class UpdateStatus extends AsyncTask< String , Void , String > {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UpdateStatusActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                Toast stat = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG);
                stat.show();

                String response = ((TextView)((LinearLayout)stat.getView()).getChildAt(0)).getText().toString();

                if(response.equals("Success")) {
                    Intent intent = new Intent(UpdateStatusActivity.this , UserHomePageActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<String ,String>();
                data.put("UserName",params[0]);
                data.put("Status",params[1]);

                String result = ruc.sendPostRequest(STATUS_URL,data);
                return result;
            }
        }
        UpdateStatus us = new UpdateStatus();
        us.execute(UserName,Status);
    }
}