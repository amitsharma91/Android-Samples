package tk.developeramit.sqlite_demo;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    String name,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DatabaseHelper(this);
    }

    public void onClickUpdateNow(View view){
        name = ((EditText)findViewById(R.id.nameUpdate)).getText().toString();
        username = ((EditText)findViewById(R.id.usernameUpdate)).getText().toString();
        password = ((EditText)findViewById(R.id.passwordUpdate)).getText().toString();

        Log.e("#Values_Extract_From_UI","TRUE");

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("username",username);
        contentValues.put("password",password);

        Log.e("#Content_Set_4_Update","TRUE");

        String studId = getIntent().getStringExtra("studId");

        Log.e("#id: ",""+studId);

        boolean updateStatus = dbHelper.updateData("Students",studId,contentValues);

        Log.e("#Update_Method_Executed","TRUE");

        if(updateStatus)
            Toast.makeText(this, "Values Updated Sucessfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Not Updated", Toast.LENGTH_SHORT).show();
    }
}