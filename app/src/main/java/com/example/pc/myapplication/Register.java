package com.example.pc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Huat Sin on 3/1/2016.
 */
public class Register  extends AppCompatActivity {

    DatabaseOpenHelper dataManager = new DatabaseOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        Button registerBtn = (Button) findViewById(R.id.Register);

        Button clearBtn = (Button) findViewById(R.id.Clear);
        //check for clear button listener
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    EditText fullName = (EditText) findViewById(R.id.fullNameEnter);
                    EditText userName = (EditText) findViewById(R.id.usernameEnter);
                    EditText passWord = (EditText) findViewById(R.id.passwordEnter);
                    EditText confPassword = (EditText) findViewById(R.id.cnfpasswordEnter);
                    Spinner roleSelect = (Spinner) findViewById(R.id.role);
                    fullName.setText("");
                    userName.setText("");
                    passWord.setText("");
                    confPassword.setText("");
                    roleSelect.setSelection(0);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.Register) {
                    try {
                        EditText fullName = (EditText) findViewById(R.id.fullNameEnter);
                        EditText userName = (EditText) findViewById(R.id.usernameEnter);
                        EditText passWord = (EditText) findViewById(R.id.passwordEnter);
                        EditText confPassword = (EditText) findViewById(R.id.cnfpasswordEnter);
                        Spinner roleSelect = (Spinner) findViewById(R.id.role);
                        TextView strView = (TextView)roleSelect.getSelectedView();

                        String fname = fullName.getText().toString();
                        String uname = userName.getText().toString();
                        String pass = passWord.getText().toString();
                        String cpass = confPassword.getText().toString();
                        String role = strView.getText().toString();

                        Log.i(role, role);

                        if (!pass.equals(cpass)) {
                            //send popup
                            Toast passw = Toast.makeText(Register.this, "Password does not match!", Toast.LENGTH_SHORT);
                            passw.show();
                        } else {
                            //insert to db
                            Detail d = new Detail();
                            d.fullname = fname;
                            d.username = uname;
                            d.password = pass;
                            d.role = role;

                            dataManager.insertDetail(d);

                            Log.i(role,role);
                            Toast passw = Toast.makeText(Register.this, uname + ", user registered!", Toast.LENGTH_SHORT);
                            passw.show();
                            ClearAll();

                        }
                    } catch (Exception ex) {
                        Toast errorBox = Toast.makeText(Register.this, "Error!", Toast.LENGTH_SHORT);
                        errorBox.show();
                    }
                }
            }
        });
    }
    public void ClearAll()
    {
        EditText fullName = (EditText) findViewById(R.id.fullNameEnter);
        EditText userName = (EditText) findViewById(R.id.usernameEnter);
        EditText passWord = (EditText) findViewById(R.id.passwordEnter);
        EditText confPassword = (EditText) findViewById(R.id.cnfpasswordEnter);
        Spinner roleSelect = (Spinner) findViewById(R.id.role);
        fullName.setText("");
        userName.setText("");
        passWord.setText("");
        confPassword.setText("");
        roleSelect.setSelection(0);
    }
}