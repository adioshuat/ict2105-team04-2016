package com.example.pc.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    DatabaseOpenHelper dataManager = new DatabaseOpenHelper(this);

    ArrayList<String> allChildren = new ArrayList<String>();

    ListView listView;

    String uname, pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //insertRawDefault();

//        //test writing to firebase
//        myFirebaseRef.child("uName").child("elderly").setValue("Ah Ma 1");

//        //create ah ma account
//        for(int i = 0; i < 10; i++)
//        {
//            myFirebaseRef.child("uName").child("ahma"+i).child("name").setValue("Ah Ma " + i);
//            myFirebaseRef.child("uName").child("ahma"+i).child("password").setValue("123");
//            myFirebaseRef.child("uName").child("ahma"+i).child("role").setValue("Elderly");
//        }
//
//        //create caretaker account
//        for(int i = 0; i < 10; i++)
//        {
//            myFirebaseRef.child("uName").child("caretaker"+i).child("name").setValue("Caretaker " + i);
//            for(int j = 0; j < 3; j++) {
//                myFirebaseRef.child("uName").child("caretaker" + i).child("elderly").child("ahma" + j).setValue("Ah Ma " + j);
//            }
//            myFirebaseRef.child("uName").child("caretaker"+i).child("password").setValue("123");
//            myFirebaseRef.child("uName").child("caretaker"+i).child("role").setValue("Caretaker");
//        }
//
//        //create caretaker account
//        for(int i = 0; i < 10; i++) {
//            myFirebaseRef.child("uName").child("family" + i).child("name").setValue("Family " + i);
//            myFirebaseRef.child("uName").child("family" + i).child("password").setValue("123");
//            myFirebaseRef.child("uName").child("family" + i).child("role").setValue("Immediate Family");
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addListenerOnButton();
    }

    public void clearAll(){

        EditText passWord = (EditText) findViewById(R.id.passwordEnter);
        passWord.setText("");

        EditText userName = (EditText) findViewById(R.id.usernameEnter);
        userName.setText("");
    }

    public void searchList() {

        //setting up firebase
        Firebase.setAndroidContext(this);
        //connecting to firebase on url
        Firebase myFirebaseRef = new Firebase("https://elderlytracker.firebaseio.com/");

        myFirebaseRef.child("uName").child("caretaker0").child("elderly").addValueEventListener(new ValueEventListener() {
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
    public void searchUserName() {

            //setting up firebase
            Firebase.setAndroidContext(this);

            //connecting to firebase on url
            Firebase myFirebaseRef = new Firebase("https://elderlytracker.firebaseio.com/");

            try {
                myFirebaseRef.child("uName").child(uname).addValueEventListener(new ValueEventListener() {
                    int i = 0;

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        try {

                            String passw = snapshot.child("password").getValue().toString();
                            Log.i("passw", passw);

                            if (pass.equals(passw)) {

                                String authenticateRole = snapshot.child("role").getValue().toString();

                                switch (authenticateRole) {
                                    case "Caretaker":
                                        Intent careTaker = new Intent(MainActivity.this, Caretaker.class);
                                        careTaker.putExtra("Username", uname);
                                        careTaker.putExtra("Role", authenticateRole);
                                        startActivity(careTaker);
                                        break;
                                    case "Elderly":
                                        Intent elderly = new Intent(MainActivity.this, Elderly.class);
                                        elderly.putExtra("Username", uname);
                                        elderly.putExtra("Role", authenticateRole);
                                        startActivity(elderly);
                                        break;
                                    case "Immediate Family":
                                        Intent immediateFam = new Intent(MainActivity.this, ImmediateFam.class);
                                        immediateFam.putExtra("Username", uname);
                                        immediateFam.putExtra("Role", authenticateRole);
                                        startActivity(immediateFam);
                                        break;
                                }
                            } else {
                                Toast passwBox = Toast.makeText(MainActivity.this, "Username/Password is not valid!", Toast.LENGTH_SHORT);
                                passwBox.show();
                            }
                        }catch(Exception ex)
                        {
                            Toast passwBox = Toast.makeText(MainActivity.this, "Username/Password is not valid!", Toast.LENGTH_SHORT);
                            passwBox.show();
                        }
                        finally {
                            clearAll();
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                        Toast errorBox = Toast.makeText(MainActivity.this, "Username/Password is not valid!", Toast.LENGTH_SHORT);
                        errorBox.show();
                    }
                });
            }
            catch (Exception ex) {
                Toast errorBox = Toast.makeText(MainActivity.this, "Username/Password is not valid!", Toast.LENGTH_SHORT);
                errorBox.show();
            }
    }
    public void addListenerOnButton() {

        Button loginBtn = (Button) findViewById(R.id.Login);
        Button clearBtn = (Button) findViewById(R.id.Clear);
        //check for clear button listener
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.Clear) {
                    clearAll();
                }
            }
        });

        //check for login button listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    EditText userName = (EditText) findViewById(R.id.usernameEnter);
                    uname = userName.getText().toString();

                    EditText passWord = (EditText) findViewById(R.id.passwordEnter);
                    pass = passWord.getText().toString();

                    if(uname.equals("") && pass.equals(""))
                    {
                        Toast errorBox = Toast.makeText(MainActivity.this, "Please fill in the text fields!", Toast.LENGTH_SHORT);
                        errorBox.show();
                    }
                    Log.i(uname, uname);
                    Log.i(pass, pass);
                    searchUserName();

                }
                catch (Exception ex) {
                    Toast errorBox = Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT);
                    errorBox.show();
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void insertRawDefault()
    {
        Detail d = new Detail();
        d.fullname="Yeo Huat Sin";
        d.username="adios";
        d.password="123";
        d.role="Caretaker";
        dataManager.insertDetail(d);
        d.fullname="Ashwini";
        d.username="ash";
        d.password="123";
        d.role="Elderly";
        dataManager.insertDetail(d);
        d.fullname="Thomas";
        d.username="thomas";
        d.password="123";
        d.role="Immediate Family";
        dataManager.insertDetail(d);
        d.fullname="Pohseng";
        d.username="poh";
        d.password="123";
        d.role="Elderly";
        dataManager.insertDetail(d);

        Log.i("insert","insert");
    }
}
