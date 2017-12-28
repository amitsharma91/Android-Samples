package tk.developeramit.socialmediaapi;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by developeramit on 28/12/17.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    public Context context;
    public AlertDialog.Builder dialog;
    public StringBuffer returningAck = new StringBuffer();
    RelativeLayout relativeLayout;

    //Constructor
    public BackgroundWorker(Context context,RelativeLayout relativeLayout) {
        this.context = context;
        this.relativeLayout = relativeLayout;
    }

    @Override
    protected String doInBackground(String... args) {

        //Input Parameters
        String type = args[0];//type of operation


        //server url
        String server_url = "http://developeramit.tk/";

        if (type.equals("register_user")) {
            String name = args[1];
            String email = args[2];
            String password = args[3];
            try {

                //create ULR
                URL url = new URL(server_url + "register.php");

                //create Connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //set method type
                connection.setRequestMethod("POST");
                //enable input & output
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //create Output Stream
                BufferedWriter brOutput = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

                //encoding Args to be send
                String encodedData = URLEncoder.encode("name") + "=" + URLEncoder.encode(name) + "&" + URLEncoder.encode("email") + "=" + URLEncoder.encode(email) + "&" + URLEncoder.encode("password") + "=" + URLEncoder.encode(password);

                //send encoded data to serverURL
                brOutput.write(encodedData);
                //flush
                brOutput.flush();
                //closing outputStream
                brOutput.close();

                //create Input Stream
                BufferedReader brInput = new BufferedReader(new InputStreamReader(connection.getInputStream(), "iso-8859-1"));
                //reading Data

                String buffer = "";

                while ((buffer = brInput.readLine()) != null) {
                    returningAck.append(buffer + "\n");
                }

                //closing InputStream
                brInput.close();
                //closing Connection
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //END OF REGISTER_USER CODE

        if (type.equals("login_user")) {
            String email = args[1];
            String password = args[2];
            try {
                //create ULR
                URL url = new URL(server_url + "login.php");

                //create Connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //set method type
                connection.setRequestMethod("POST");

                //enable input & output
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //create Output Stream
                BufferedWriter brOutput = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

                //encoding Args to be send
                String encodedData = URLEncoder.encode("email") + "=" + URLEncoder.encode(email) + "&" + URLEncoder.encode("password") + "=" + URLEncoder.encode(password);

                //send encoded data to serverURL
                brOutput.write(encodedData);

                //flush
                brOutput.flush();

                //closing outputStream
                brOutput.close();

                //create Input Stream
                BufferedReader brInput = new BufferedReader(new InputStreamReader(connection.getInputStream(), "iso-8859-1"));
                //reading Data

                String buffer = "";

                while ((buffer = brInput.readLine()) != null) {
                    returningAck.append(buffer + "\n");
                }

                //closing InputStream
                brInput.close();

                //closing Connection
                connection.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return returningAck.toString();
    }

    @Override
    protected void onPreExecute() {
        relativeLayout.setVisibility(View.VISIBLE);
        dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(true);
        dialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String aVoid) {
        dialog.setMessage(returningAck.toString());
        dialog.show();
        relativeLayout.setVisibility(View.GONE);
        returningAck.setLength(0);//clear StringBuffer
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}