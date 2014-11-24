package com.shaheed.online29;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


public class RegistrationActivity extends Activity {

    private Button registerButton,backButton;
    private EditText nameText,emailText,passText;
    private TextView statusText;

    private boolean isValid;

    private JsonParser jsonParser = new JsonParser();
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViewsById();

        implementButtons();
    }

    private void implementButtons() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nameText.getText().toString().equals("") || emailText.getText().toString().equals("") || passText.getText().toString().equals("")){
                    isValid = false;
                }else{
                    isValid = true;
                }

                if(isValid){

                    new RegisterUserToDatabase().execute(nameText.getText().toString(),emailText.getText().toString(),passText.getText().toString());

                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), StartActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });
    }

    private void findViewsById() {
        registerButton = (Button) findViewById(R.id.buttonRegister);
        backButton = (Button) findViewById(R.id.buttonBack);

        nameText = (EditText) findViewById(R.id.textName);
        emailText = (EditText) findViewById(R.id.textEmail);
        passText = (EditText) findViewById(R.id.textPassword);

        statusText = (TextView) findViewById(R.id.textStatus);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class RegisterUserToDatabase extends AsyncTask<String, String, String>{

        private boolean isDone = false;

        @Override
        protected String doInBackground(String... strings) {
            String name = strings[0];
            String email = strings[1];
            String password = strings[2];

            List<NameValuePair> registrationInfo = new ArrayList<NameValuePair>();
            registrationInfo.add(new BasicNameValuePair("name", name));
            registrationInfo.add(new BasicNameValuePair("email", email));
            registrationInfo.add(new BasicNameValuePair("password", password));

            jsonObject = jsonParser.makeHTTPRequest(Constants.URL_REGISTRATION,"GET",registrationInfo);

            String reply = null;

            try{
                reply = jsonObject.getString("reply");

                if(reply.equals("done")){
                    isDone = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(isDone) {
                statusText.setText(R.string.RegistrationComplete);
                statusText.setVisibility(View.VISIBLE);
            }
            else{
                statusText.setText("Something went wrong.Registration Incomplete.\nNote that only 1 account is allowed per email address");
                statusText.setVisibility(View.VISIBLE);
            }
        }
    }
}
