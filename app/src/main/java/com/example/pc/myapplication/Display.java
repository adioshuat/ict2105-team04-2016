package com.example.pc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Huat Sin on 3/1/2016.
 */
public class Display extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        String username = getIntent().getStringExtra("Username");
        String selectedGrandma = getIntent().getStringExtra("GrandMaSelected");
        TextView uname = (TextView) findViewById(R.id.userNameText);
        uname.setText(username);

        //setti ng up firebase
        Firebase.setAndroidContext(this);

        //connecting to firebase on url
        final Firebase myFirebaseRef = new Firebase("https://elderlytracker.firebaseio.com/");

        ArrayList<String> allChildren = new ArrayList<String>();

        myFirebaseRef.child("uName").child(selectedGrandma).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                EditText userName = (EditText) findViewById(R.id.username);
                EditText userRole = (EditText) findViewById(R.id.userRole);
                EditText userPassword = (EditText) findViewById(R.id.password);
                userName.setText(snapshot.child("name").getValue().toString());
                userRole.setText(snapshot.child("role").getValue().toString());
                userPassword.setText(snapshot.child("password").getValue().toString());


                //edit fields
                myFirebaseRef.child("uName").child("caretaker0").child("password").setValue("321");
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }

        });
    }
}
