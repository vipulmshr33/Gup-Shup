package com.example.vipul.gup_shup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UserHomePageActivity extends AppCompatActivity {

    String JSON_String;

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    StatusAdapter statusAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;
        @Override
        protected void onPreExecute() {
            json_url = "http://vipulmshr.16mb.com/gupshup/fetch_status_json.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_String = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(JSON_String+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String  result) {

            json_string = result;

            listView = (ListView)findViewById(R.id.listview);

            statusAdapter = new StatusAdapter(UserHomePageActivity.this, R.layout.row_layout);
            listView.setAdapter(statusAdapter);
            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("result");
                int count = 0;
                String username, status;
                while (count<jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    username = JO.getString("USERNAME");
                    status = JO.getString("STATUS");
                    com.example.vipul.gup_shup.Status status1 = new com.example.vipul.gup_shup.Status(username,status);
                    statusAdapter.add(status1);
                    count++;
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.updateStatus:
                Intent updateStatusIntent = new Intent(UserHomePageActivity.this , UpdateStatusActivity.class);
                startActivity(updateStatusIntent);
                break;

            case R.id.logoutUser:

                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout(){

        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(LoginConfig.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(LoginConfig.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to username
                        editor.putString(LoginConfig.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(UserHomePageActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
