package com.example.pc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Huat Sin on 3/1/2016.
 */
public class Caretaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.caretaker);

        String username = getIntent().getStringExtra("Username");
        String role = getIntent().getStringExtra("Role");

        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome " + username + ", role: " + role);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        Button manageBtn = (Button) findViewById(R.id.manageBtn);

        manageBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.manageBtn) {
                    String username = getIntent().getStringExtra("Username");
                    Intent careTaker = new Intent(Caretaker.this, ElderlyList.class);
                    careTaker.putExtra("Username", username);
                    startActivity(careTaker);
                }
            }
        });
    }

    //log out button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}