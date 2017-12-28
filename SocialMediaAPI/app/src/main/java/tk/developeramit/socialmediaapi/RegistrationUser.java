package tk.developeramit.socialmediaapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegistrationUser extends AppCompatActivity {

    private EditText nameText,emailText,passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameText = (EditText) findViewById(R.id.name_signup);
        emailText = (EditText) findViewById(R.id.email_signup);
        passwordText = (EditText) findViewById(R.id.passowrd_signup);
    }

    public void onClickRegistration(View view) {
        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.progress_layout_signup);

        if(name.equalsIgnoreCase("")){
            nameText.setError("Name cannot be Empty");
            return;
        }

        if(email.equalsIgnoreCase("")){
            emailText.setError("Email cannot be Empty");
            return;
        }
        if(password.equalsIgnoreCase("")){
            passwordText.setError("Password cannot be Empty");
            return;
        }
        //Toast.makeText(this, name + " : " + email + " : " + password, Toast.LENGTH_SHORT).show();

        BackgroundWorker backgroundWorker = new BackgroundWorker(this,relativeLayout);
        backgroundWorker.execute("register_user",name,email,password);
    }

    public void onClickAlready(View view){
        startActivity(new Intent(RegistrationUser.this,MainActivity.class));
    }
}
