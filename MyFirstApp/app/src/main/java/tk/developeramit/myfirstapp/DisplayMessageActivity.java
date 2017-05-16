package tk.developeramit.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //Get the Intent that started this  Activity and Extract the String
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Capture the Layout TextView and Set the String as its text
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("\""+message+"\"");
    }
}
