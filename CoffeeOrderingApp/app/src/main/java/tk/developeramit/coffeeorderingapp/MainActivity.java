package tk.developeramit.coffeeorderingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    public int quantity = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        textView.setText("" + NumberFormat.getCurrencyInstance().format(quantity * 5)+", for "+quantity+" cups");
    }
}