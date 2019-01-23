package com.example.jonnyjonny.whatsapper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class RealLoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtLoginEmail,edtLoginPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_login);
        setTitle("Log In");
        edtLoginEmail=findViewById(R.id.edtLoginEmail);
        edtLoginPassword=findViewById(R.id.edtLoginPassword);
        loginBtn=findViewById(R.id.btnRealLogin);
        loginBtn.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRealLogin:
                ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null&&e==null){
                            Toast.makeText(RealLoginActivity.this,user.getUsername()+"Is Logged In",Toast.LENGTH_SHORT).show();
                            transitionToUsersActivity();


                        }else {
                            Toast.makeText(RealLoginActivity.this,"Failed To Login:"+e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                break;
        }

    }
    private void transitionToUsersActivity(){
        Intent intent=new Intent(RealLoginActivity.this,UsersActivity.class);
        startActivity(intent);
}}
