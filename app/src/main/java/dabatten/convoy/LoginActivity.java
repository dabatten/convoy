package dabatten.convoy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends Activity {

    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;
    private Button signupButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(getApplicationContext(), MainActivity.class);

        //check if user is already logged in
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //next activity
            startActivity(intent);
            //TODO
        }

        setContentView(R.layout.activity_login);
        //get view elements
        usernameField = (EditText) findViewById(R.id.loginUsername);
        passwordField = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        signupButton = (Button) findViewById(R.id.signupButton);

        //set button listeners

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });




    }

    private void signUp(){
        //get user details from view
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        ParseUser user = new ParseUser();
        //set user details
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    //login and move on to next activity
                    //TODO
                    Toast.makeText(getApplicationContext(),
                            "Sign up success!", Toast.LENGTH_LONG).show();
                    login();
                }
                else {
                    //error occurred, log details and tell user
                    Log.w("parse_error", e);
                    Toast.makeText(getApplicationContext(),
                            "Sign up failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void login(){
        //get credentials
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    //successful login, move on to next activity
                    //TODO
                    Toast.makeText(getApplicationContext(),
                            "Login success!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else {
                    //error occurred, log details and tell user
                    Log.w("parse_error", e);
                    Toast.makeText(getApplicationContext(),
                            "Login failed!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroy(){
        //TODO
        super.onDestroy();
    }
}
