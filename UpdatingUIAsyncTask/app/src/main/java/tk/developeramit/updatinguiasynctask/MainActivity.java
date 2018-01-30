package tk.developeramit.updatinguiasynctask;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;
    private ProgressBar progressBar;
    private BackgroundWorkerLocal backgroundWorkerLocal;
    private Button startButton, stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = (TextView) findViewById(R.id.status_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        startButton = (Button) findViewById(R.id.startAsync_btn);
        stopButton = (Button) findViewById(R.id.stopAsync_btn);
    }


    public void onClickStartTask(View view) {
        backgroundWorkerLocal = new BackgroundWorkerLocal();
        backgroundWorkerLocal.execute();
    }

    public void onClickClearStatus(View view) {
        statusText.setText("");
        progressBar.setProgress(0);
    }

    public void onClickStopTask(View view) {
        backgroundWorkerLocal.cancel(true);
        backgroundWorkerLocal = null;
        startButton.setEnabled(false);
        startButton.setBackgroundColor(Color.GREEN);
        startButton.setTextColor(Color.GRAY);
        stopButton.setEnabled(false);
        stopButton.setBackgroundColor(Color.RED);
        stopButton.setTextColor(Color.GRAY);
    }

    class BackgroundWorkerLocal extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            int i = 0;
            for (; i <= 100; i++) {
                SystemClock.sleep(200);
                publishProgress(i);
                if (i == 100)
                    return "" + i;
            }

            return "" + i;
        }


        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            statusText.setText("Completed " + aVoid + "%");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            statusText.setText("Progress is - " + values[0] + "%");
            super.onProgressUpdate(values);
        }
    }

}