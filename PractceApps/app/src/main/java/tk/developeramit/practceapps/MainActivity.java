package tk.developeramit.practceapps;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button optionButton = (Button) findViewById(R.id.option_button);
        registerForContextMenu(optionButton);
    }

    /*Context Menu Start*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_1: {
                Toast.makeText(this, "Option 1 is Selected", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.option_2: {
                Toast.makeText(this, "Option 2 is Selected", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.option_3: {
                Toast.makeText(this, "Option 3 is Selected", Toast.LENGTH_SHORT).show();
                return true;
            }

        }
        return true;
    }
    /*End of Context Menu*/

    /*Option Menu Start*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Toast.makeText(this, "Settings coming soon...", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.profile) {
            Toast.makeText(this, "Working on Profile", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    /*End of Option Menu*/


    /*InboxStyle Notification Start*/
    public void onNotificationClick(View view) {
        //Retrieve An Instance of NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //Apply Style by creating  instance of NotificationCompat.InboxStyle
        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle(builder);

        //Adding Lines to Style
        style.addLine("This is Line 1 kjfd fdgjkdfkj g dfdfg hf bgkhbfgkjf gj jkfg j fgjkdkfj gkh f");
        /*
        style.addLine("This is Line 2");
        style.addLine("This is Line 3");
        style.addLine("This is Line 4");
        style.addLine("This is Line 5");
        style.addLine("This is Line 6");
        style.addLine("This is Line 7");
        style.addLine("This is Line 8");
        */


        //Adding Title to Notification
        style.setBigContentTitle("My Title 1");

        //Adding summary of Notification
        style.setSummaryText("This is a simple Summary");

        //Build a Notification
        Notification notification = builder.setContentTitle("My TITLEEE").setContentText("This is Contet Text").setSmallIcon(R.drawable.delete).build();

        //Notification Manager (Passing Notification to Notification Manager)
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0x1234, notification);
    }
    /*End of InboxStyle Notification*/

    /*Start of BigText Notification*/
    public void onNotificationBigTextClick(View view) {
        // This type can hold 450 Characters and remaing text will be trimmed

        //Retrieve An Instance of NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //Apply Style by creating  instance of NotificationCompat.BigTextStyle
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle(builder);

        //Setting the Long Text
        style.bigText("This a long Text, jhksdf hjskdhfljsjf sljdfh jsdh fkvbbzxmgdfhsadgflhweiufhlsdfhk  ksjdhfklj shdklf ssj hlksjdhfklhasdkjhf lsad fshflk jskldf kasdfkahsdlhvbs fdhg kffds hgf ggljkg askg ks gkj");

        //Setting Title
        style.setBigContentTitle("This is BigText Title");

        //Setting Summary
        style.setSummaryText("This is BigText Summary");

        //Build a Notification
        Notification notification = builder.setContentTitle("Alternaet Title").setContentText("Alternate Conetent Text").setSmallIcon(R.drawable.cloud).build();

        //Notification Manager (Passing Notification to Notification Manager)
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);//this -> context
        notificationManager.notify(0x1235, notification);
    }
    /*End of BigText Notification*/

    /*Start of BigPictureStyle Notification*/
    public void onNotificationBigPictureClick(View view) {
        //Retrieve An Instance of NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);//this -> context

        //Apply Style by creating  instance of NotificationCompat.BigPictureStyle
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle(builder);

        //Adding image to be displayed. (Bitmap format)
        final Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.imagecodeathon);
        style.bigPicture(picture);

        //Optional LargeIcon
        //style.bigLargeIcon()

        //Setting different Title
        style.setBigContentTitle("This is BigPictureStyle Title");

        //Setting Summary
        style.setSummaryText("This is Summary Text");

        //Build Notification
        Notification notification = builder.setContentTitle("Alternate Title").setContentText("This is Alternaet Text").setSmallIcon(R.drawable.activate).build();

        //Notification Manger
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0x1236, notification);

    }
    /*End of BigPictureStyle Notification*/

    /*For ListView Button*/
    public void onButtonClick(View view) {
        Intent intentListView = new Intent(MainActivity.this, AdapterActivity.class);
        startActivity(intentListView);
    }
}
