package tk.developeramit.json_parsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String jsonContact;
    private AlertDialog.Builder alertBuilder;
    private ArrayList<HashMap<String, String>> contactList;
    private ListAdapter adapter;
    private RelativeLayout relativeLayout;
    private String jsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonContact = "https://api.androidhive.info/contacts/";
        lv = (ListView) findViewById(R.id.listView);
        contactList = new ArrayList<>();
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_home);

        new BackgroundHelperForMainActivity().execute();
    }

    /**
     * To Show JSON STRING
     */

    public void onClickViewHome(View view) {
        alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("JSON String");
        alertBuilder.setMessage(jsonString);
        alertBuilder.show();
    }

    /**
     * Class for JSON Thread
     */
    private class BackgroundHelperForMainActivity extends AsyncTask<Void, Void, Void> {

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
                        contactItem.put("id", id);
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
            relativeLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.my_list, new String[]{"name", "email", "mobile"}, new int[]{R.id.name, R.id.email, R.id.contact});
            lv.setAdapter(adapter);

            relativeLayout.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }//end of BackgroundHelperForMainActivity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_data: {
                Intent viewData = new Intent(this, view_data.class);
                startActivity(viewData);
            }
            break;
        }
        return true;
    }
}