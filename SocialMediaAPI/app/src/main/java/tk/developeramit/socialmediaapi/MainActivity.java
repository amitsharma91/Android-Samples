package tk.developeramit.socialmediaapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public EditText emailText;
    public EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.passowrd);
    }

    public void onClickSignup(View view) {
        startActivity(new Intent(MainActivity.this, RegistrationUser.class));
    }

    public void onLogin(View view) {
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.progress_layout);

        if (email.trim().equalsIgnoreCase("")) {
            emailText.setError("Email Cannot be Empty!");
            return;
        }

        if (password.trim().equalsIgnoreCase("")) {
            passwordText.setError("Password Cannot be Empty!");
            return;
        }

        //Toast.makeText(this, email + " : " + password, Toast.LENGTH_SHORT).show();

        BackgroundWorker backgroundWorker = new BackgroundWorker(this, relativeLayout);
        backgroundWorker.execute("login_user", email, password);
    }
}
