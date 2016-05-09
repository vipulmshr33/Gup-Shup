package com.example.vipul.gup_shup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonProceed = (Button)findViewById(R.id.proceedMainButton);

        buttonProceed.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if(view == buttonProceed) {
            Intent intent = new Intent(MainActivity.this , LoginActivity.class);
            startActivity(intent);
        }
    }


}
