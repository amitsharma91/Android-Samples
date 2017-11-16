package tk.developeramit.sqlite_demo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    String name, username, password, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

        Toast.makeText(this, "Database & Table Created", Toast.LENGTH_SHORT).show();
    }

    public void onClickInsert(View view) {
        name = ((EditText) findViewById(R.id.name)).getText().toString();
        username = ((EditText) findViewById(R.id.username)).getText().toString();
        password = ((EditText) findViewById(R.id.password)).getText().toString();

        //setting ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("username", username);
        contentValues.put("password", password);

        //invoke insert method from DatabaseHelper
        boolean insertStatus = dbHelper.insertData("Students", contentValues);

        if (insertStatus)
            Toast.makeText(this, "Data Inserted Sucessfully", Toast.LENGTH_SHORT).show();
        else if (!insertStatus)
            Toast.makeText(this, "Failed to insert Data", Toast.LENGTH_SHORT).show();
    }


    public void onClickShowAll(View view) {
        String selectQuery = "SELECT * FROM Students";

        Cursor cursor = dbHelper.showAllData(selectQuery, null);

        if (cursor.getCount() == 0) {
            displayMessageInAlertDialog("ERROR", "Empty Set / Failed to retrieve Data");
            return;
        }

        //store result
        StringBuffer dataBuffer = new StringBuffer();

        while (cursor.moveToNext()) {
            dataBuffer.append("ID: " + cursor.getInt(0) + "\n");
            dataBuffer.append("Name: " + cursor.getString(1) + "\n");
            dataBuffer.append("Username: " + cursor.getString(2) + "\n");
            dataBuffer.append("Password: " + cursor.getString(3) + "\n\n");
        }

        //display dataBuffer
        displayMessageInAlertDialog("Data", dataBuffer.toString());
    }


    public void displayMessageInAlertDialog(String title, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.show();
    }

    public void onClickUpdateRecord(View view) {
        id = ((EditText) findViewById(R.id.studentId)).getText().toString();

        Intent intentUpdate = new Intent(this, UpdateActivity.class);
        intentUpdate.putExtra("studId", "" + id);
        startActivity(intentUpdate);

    }
}