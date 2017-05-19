package tk.developeramit.coffeeorderingapp;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    public int quantity = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public boolean cream, chocolate;

    public void getCream(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.cream_checkbox);
        cream = checkBox.isChecked();
    }

    public void getChocolate(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        chocolate = checkBox.isChecked();
    }



    public void incrementButton(View view) {
        TextView textView = (TextView) findViewById(R.id.quantity_text_view);
        textView.setText("" + (++quantity));
    }

    public void decrementButton(View view) {
        TextView textView = (TextView) findViewById(R.id.quantity_text_view);
        if (quantity > 0)
            textView.setText("" + (--quantity));
    }

    public void setOrder(View view) {
        TextView textView = (TextView) findViewById(R.id.cost_text_view);
        int totalCost = (quantity * 5);
        String msg = "Thank you! for ordering, "+((EditText)findViewById(R.id.name_edit_text)).getText().toString()+"\n";

        if (cream) {
            totalCost = totalCost + (1*quantity);
            msg = msg + "Whipped Cream: " + NumberFormat.getCurrencyInstance().format(1 * quantity) + "\n";
        }
        if (chocolate) {
            totalCost = totalCost + (2*quantity);
            msg = msg + "Chocolate: " + NumberFormat.getCurrencyInstance().format(2 * quantity) + "\n";
        }

        msg = msg + "Total Cost: " + NumberFormat.getCurrencyInstance().format(totalCost) + "\n";

        textView.setText(msg);

        Toast.makeText(MainActivity.this, "Your Order has been accepted", Toast.LENGTH_SHORT).show();
    }
}