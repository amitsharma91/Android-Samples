package tk.developeramit.json_parsing;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class view_data extends AppCompatActivity {

    private String phpURL;
    private AlertDialog.Builder alertBuilder;
    private ListView lvStudent;
    private ArrayList<HashMap<String, String>> studentList;
    private RelativeLayout relativeLayout;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        lvStudent = (ListView) findViewById(R.id.student_list);
        studentList = new ArrayList<>();
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_view_data);

        phpURL = "http://192.168.0.2/MyProjects/Android_WS/sthudentJSON.php";

        new BackgroundWorkerForViewData().execute();
    }

    /**
     * To Show JSON STRING
     */

    public void onClickViewData(View view) {
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("JSON String");
        alertBuilder.setMessage(jsonString);
        alertBuilder.show();
    }


    /**
     * Background Worker for parsing data from Mysql
     */

    private class BackgroundWorkerForViewData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            jsonString = getStringFromURL(phpURL);

            if (jsonString != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject studentElement = jsonArray.getJSONObject(i);

                        String student_name = studentElement.getString("name");
                        String student_class = studentElement.getString("class");
                        String student_email = studentElement.getString("email");
                        String student_marks = studentElement.getString("marks");

                        HashMap<String, String> students = new HashMap<>();
                        students.put("student_name", student_name);
                        students.put("student_class", student_class);
                        students.put("student_email", student_email);
                        students.put("student_marks", student_marks);

                        studentList.add(students);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(view_data.this, "Unable to read JSON", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            relativeLayout.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //Setting List Data

            ListAdapter adapter = new SimpleAdapter(view_data.this, studentList, R.layout.my_list_student, new String[]{"student_name", "student_email", "student_class", "student_marks"}, new int[]{R.id.student_name, R.id.student_email, R.id.student_class, R.id.student_marks});
            lvStudent.setAdapter(adapter);

            relativeLayout.setVisibility(View.GONE);
        }


        //Method to get json string from php
        public String getStringFromURL(String resourceURL) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            InputStream inputStream = null;
            try {
                URL url = new URL(resourceURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

            } catch (Exception e) {
                Log.e("" + this.getClass().getName(), "" + e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return stringBuilder.toString();
        }
    }
}