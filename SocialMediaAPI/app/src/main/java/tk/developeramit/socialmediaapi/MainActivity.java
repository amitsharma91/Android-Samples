package tk.developeramit.socialmediaapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //For Google Login
    private LinearLayout profileInfo, primaryView;
    private TextView profileName;
    private TextView profileEmail;
    private ImageView profileImage;
    private Button logoutButton;
    private SignInButton signInButton;
    private static final int REQUEST_CODE = 9001;
    //For Google API Client
    private GoogleApiClient googleApiClient;
    //For Sign in Option
    private GoogleSignInOptions googleSignInOptions;

    //For App Login
    private EditText emailText;
    private EditText passwordText;

    //For Progress
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Relative Layout for Progress
        relativeLayout = (RelativeLayout) findViewById(R.id.progress_layout);

        //initiate For App Login
        emailText = (EditText) findViewById(R.id.email);
        passwordText = (EditText) findViewById(R.id.passowrd);


        //initiate For Google Sign-in
        profileInfo = (LinearLayout) findViewById(R.id.profile_info);
        primaryView = (LinearLayout) findViewById(R.id.primary_view);
        profileName = (TextView) findViewById(R.id.profile_name);
        profileImage = (ImageView) findViewById(R.id.profile_picture);
        profileEmail = (TextView) findViewById(R.id.profile_email);
        logoutButton = (Button) findViewById(R.id.profile_logout);
        signInButton = (SignInButton) findViewById(R.id.google_sign_in);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions).build();


        //
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkConnection()) {
                    //create intent
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

                    //start activity for result
                    startActivityForResult(signInIntent, REQUEST_CODE);
                } else {
                    Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //For Sign In Button
    public void onClickSignup(View view) {
        startActivity(new Intent(MainActivity.this, RegistrationUser.class));
    }

    //For Login Button
    public void onLogin(View view) {
        if (checkConnection()) {
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            if (email.trim().equalsIgnoreCase("")) {
                emailText.setError("Email Cannot be Empty!");
                return;
            }

            if (password.trim().equalsIgnoreCase("")) {
                passwordText.setError("Password Cannot be Empty!");
                return;
            }

            BackgroundWorker backgroundWorker = new BackgroundWorker(this, relativeLayout);
            backgroundWorker.execute("login_user", email, password);
        } else {
            Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }//End of Login Button Code


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Accessing the Result
        GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        if (googleSignInResult.isSuccess()) {
            //Get SignInAccount via SignInResult
            GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
            profileName.setText(googleSignInAccount.getDisplayName());//set Profile Name
            profileEmail.setText(googleSignInAccount.getEmail());//set Profile Email

            if (googleSignInAccount.getPhotoUrl() != null) {
                String url = googleSignInAccount.getPhotoUrl().toString().trim();
                //Toast.makeText(this, "" + url, Toast.LENGTH_SHORT).show();

                //Set Image using Glide Repository
                Glide.with(MainActivity.this).load(url).apply(new RequestOptions().override(144, 165)).into(profileImage);
            }

            //Now Show Profile Info and Change UI
            profileInfo.setVisibility(View.VISIBLE);//visible profile Information)
            primaryView.setVisibility(View.GONE);//Hide Primary View
        } else {
            profileInfo.setVisibility(View.GONE);//visible profile Information
            primaryView.setVisibility(View.VISIBLE);//Hide Primary View
        }
    }

    //For Sign Out of Google Account
    public void onClickSignOut(View view) {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                profileImage.setImageResource(R.drawable.googlelogo);
                profileInfo.setVisibility(View.GONE);//visible profile Information
                primaryView.setVisibility(View.VISIBLE);//Hide Primary View
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Some Problem in Connection.", Toast.LENGTH_SHORT).show();
    }


    //Check Connection
    public boolean checkConnection() {
        boolean wifi = false, mobileData = false;

        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectionManager.getAllNetworkInfo();

        for (NetworkInfo ni : networkInfos) {

            if (ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected())
                wifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected())
                mobileData = true;
        }

        return (wifi || mobileData);
    }
}