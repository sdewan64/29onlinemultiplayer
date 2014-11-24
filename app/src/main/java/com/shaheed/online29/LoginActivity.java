package com.shaheed.online29;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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


public class LoginActivity extends Activity {

    private Button loginButton;
    private EditText emailBox,passwordBox;
    private TextView statusText;

    private JSONObject jsonObject;
    private JsonParser jsonParser = new JsonParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewsById();
        implementButtons();

    }

    private void implementButtons() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginDataFetch().execute(emailBox.getText().toString(),passwordBox.getText().toString());
            }
        });
    }

    private void findViewsById() {
        loginButton = (Button) findViewById(R.id.buttonLogin);
        emailBox = (EditText) findViewById(R.id.edittextEmail);
        passwordBox = (EditText) findViewById(R.id.edittextPassword);
        statusText = (TextView) findViewById(R.id.textviewStatus);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    private class LoginDataFetch extends AsyncTask<String, String, String>{
        private Boolean isDone = false;
        String replyMsg;

        @Override
        protected String doInBackground(String... strings) {

            String email = strings[0];
            String password = strings[1];

            //Log.d("stringLog","string0:"+email);
            //Log.d("stringLog","string1:"+password);

            List<NameValuePair> loginInfo = new ArrayList<NameValuePair>();

            loginInfo.add(new BasicNameValuePair("email", email));
            loginInfo.add(new BasicNameValuePair("password", password));

            jsonObject = jsonParser.makeHTTPRequest(Constants.URL_LOGIN,"GET",loginInfo);
            String reply = null;

            try{
                reply = jsonObject.getString("reply");

                if(reply.equals("done")){
                    isDone = true;
                    Variables.username = jsonObject.getString("username");
                }else{
                    replyMsg = reply;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(isDone){

                //user found redirect to account menu

                Intent in = new Intent(getApplicationContext(), AccountMenu.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();

            }else{
                //setting the statusText as the error replied from server
                statusText.setText(replyMsg);
                statusText.setVisibility(View.VISIBLE);
            }

        }
    }

}
