package tk.developeramit.json_parsing;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String jsonContact;
    AlertDialog.Builder alertBuilder;
    ArrayList<HashMap<String, String>> contactList;
    ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonContact = "https://api.androidhive.info/contacts/";
        lv = (ListView) findViewById(R.id.listView);
        contactList = new ArrayList<>();


        new BackgroundHelperForMainActivity().execute();
    }

    /**
     * Class for JSON Thread
     */
    private class BackgroundHelperForMainActivity extends AsyncTask<Void, Void, Void> {

        public String jsonString = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HandleJSON handleJSON = new HandleJSON();

                //get JSON in String Format
                jsonString = handleJSON.getJSONFromUrl(jsonContact);

                if (jsonString != null) {

                    JSONObject jsonObject = new JSONObject(jsonString);

                    JSONArray contact = jsonObject.getJSONArray("contacts");

                    for (int i = 0; i < contact.length(); i++) {
                        JSONObject jsonData = contact.getJSONObject(i);

                        String id = jsonData.getString("id");
                        String name = jsonData.getString("name");
                        String email = jsonData.getString("email");
                        String address = jsonData.getString("address");
                        String gender = jsonData.getString("gender");

                        JSONObject phone = jsonData.getJSONObject("phone");

                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");


                        HashMap<String, String> contactItem = new HashMap<>();
                        contactItem.put("id",id);
                        contactItem.put("name", name);
                        contactItem.put("email", email);
                        contactItem.put("mobile", mobile);

                        contactList.add(contactItem);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Unable to Read JSON script", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.e("" + this.getClass().getName(), "" + e);
                Toast.makeText(MainActivity.this, "Some Problem in HandleJSON", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("JSON String");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            alertBuilder.setMessage(jsonString);
            alertBuilder.show();

            adapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.my_list, new String[]{"name", "email", "mobile"}, new int[]{R.id.name, R.id.email, R.id.contact});

            lv.setAdapter(adapter);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}