package com.example.jonnyjonny.whatsapper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail,edtUsername,edtPassword;
    private Button btnLogin,btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("SignUp");
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN){

                    onClick(btnSignup);
                }
                return false;
            }
        });
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null){
            transitionToUsersActivity();
            //ParseUser.getCurrentUser().logOut();

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignUp:
                if(edtEmail.getText().toString().equals("")||edtUsername.getText().toString().equals("")||edtPassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this,"Email,Password,Username Is Required!",Toast.LENGTH_SHORT).show();


                }else {
                final ParseUser appUser=new ParseUser();
                appUser.setEmail(edtEmail.getText().toString());
                appUser.setUsername(edtUsername.getText().toString());
                appUser.setPassword(edtPassword.getText().toString());
                final ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Signing Up "+edtUsername.getText().toString());
                progressDialog.show();
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(LoginActivity.this, appUser.getUsername() + "Is Signed Up!", Toast.LENGTH_SHORT).show();
                            transitionToUsersActivity();

                        } else {
                            Toast.makeText(LoginActivity.this, "There was an error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

                }
                break;
            case R.id.btnLogin:
                Intent intent=new Intent(LoginActivity.this,RealLoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
    public void rootLayoutTapped(View view){
        try{
        InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }catch (Exception e){
            e.printStackTrace();
        }
}
private void transitionToUsersActivity(){
        Intent intent=new Intent(LoginActivity.this,UsersActivity.class);
        startActivity(intent);
}
}
