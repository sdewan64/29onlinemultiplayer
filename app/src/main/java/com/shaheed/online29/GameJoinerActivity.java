package com.shaheed.online29;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GameJoinerActivity extends Activity {

    JSONObject jsonObject;
    JsonParser jsonParser = new JsonParser();

    TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_joiner);
        findViewsById();
        new SetIdForGame().execute();
    }

    private void findViewsById() {
        textViewStatus = (TextView) findViewById(R.id.joingameTextviewStatus);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_joiner, menu);
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

    public class SetIdForGame extends AsyncTask<String, String, String> {

        private Boolean isDone = false;
        private String actionId = "0";

        @Override
        protected String doInBackground(String... strings) {
            String gameId = Constants.gameId;
            String name = Constants.username;

            List<NameValuePair> userData = new ArrayList<NameValuePair>();
            userData.add(new BasicNameValuePair("gameid", gameId));
            userData.add(new BasicNameValuePair("username", name));

            jsonObject = jsonParser.makeHTTPRequest(Constants.URL_JOINGAME,Constants.METHOD_GET, userData);

            String reply = null;

            try {
                reply = jsonObject.getString("reply");

                if(reply.equals("done")){
                    isDone = true;
                    actionId = jsonObject.getString("actionid");
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
                Constants.actionUserId = Integer.parseInt(actionId);

                Intent in = new Intent(getApplicationContext(), GameActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        }
    }
}
