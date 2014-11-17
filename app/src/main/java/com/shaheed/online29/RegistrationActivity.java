package com.shaheed.online29;

import android.app.Activity;
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

    Button registerButton,backButton;
    EditText nameText,emailText,passText;
    TextView statusText;

    private boolean isValid;

    JsonParser jsonParser = new JsonParser();
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViewsById();

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

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name",name));
            params.add(new BasicNameValuePair("email",email));
            params.add(new BasicNameValuePair("password",password));

            jsonObject = jsonParser.makeHTTPRequest(Constants.URL_REGISTRATION,"GET",params);

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

            if(isDone) statusText.setVisibility(View.VISIBLE);
            else{
                statusText.setText("Something went wrong.Registration Incomplete.\nNote that only 1 account is allowed per email address");
                statusText.setVisibility(View.VISIBLE);
            }
        }
    }
}
