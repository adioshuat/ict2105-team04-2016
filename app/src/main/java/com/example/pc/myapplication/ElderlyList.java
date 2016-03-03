package com.example.pc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Huat Sin on 3/3/2016.
 */
public class ElderlyList extends AppCompatActivity {

    ArrayList<String> allChildren = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.elderly_list);

        searchList();
        populateListView();

    }

    public void searchList() {

        String username = getIntent().getStringExtra("Username");
        //setting up firebase
        Firebase.setAndroidContext(this);
        //connecting to firebase on url
        Firebase myFirebaseRef = new Firebase("https://elderlytracker.firebaseio.com/");

        myFirebaseRef.child("uName").child(username).child("elderly").addValueEventListener(new ValueEventListener() {
            int i = 0;

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
                //EditText userName = (EditText) findViewById(R.id.usernameEnter);

                snapshot.child("password").getValue();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    allChildren.add(postSnapshot.getKey().toString());
                    Log.i("Grandma", allChildren.toString());
                    i++;
                }
                //edit fields
                //myFirebaseRef.child("uName").child("caretaker0").child("password").setValue("321");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }

    public void populateListView() {
        //String username = getIntent().getStringExtra("Username");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, allChildren);

        // Get ListView object from xml
        final ListView listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

                String username = getIntent().getStringExtra("Username");
                Intent display = new Intent(ElderlyList.this, Display.class);
                display.putExtra("Username", username);
                display.putExtra("GrandMaSelected", itemValue);
                startActivity(display);
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
