package com.shaheed.online29;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AccountMenuActivity extends Activity {

    private Activity selfActivity;
    private TextView textViewUserName;
    private Button buttonCreateGame,buttonJoinGame,buttonQuit;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);
        selfActivity = this;
        findViewsById();
        implementButtons();

        //setting username
        textViewUserName.setText("Hi "+Constants.username);
    }

    private void implementButtons() {
        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.quitButton(selfActivity);
            }
        });

        buttonCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateGameRoom().execute(Constants.username);
            }
        });
        buttonJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), GameListActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });
    }

    private void findViewsById() {
        textViewUserName = (TextView) findViewById(R.id.textviewUsername);
        buttonCreateGame = (Button) findViewById(R.id.buttonCreateNewGame);
        buttonJoinGame = (Button) findViewById(R.id.buttonEnterCreatedGame);
        buttonQuit = (Button) findViewById(R.id.buttonQuit);
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account_menu, menu);
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

    class CreateGameRoom extends AsyncTask<String,String,String>{

        private boolean isDone = false;
        private JSONObject jsonObject;
        private JsonParser jsonParser = new JsonParser();

        @Override
        protected String doInBackground(String... strings) {

            String name = strings[0];

            List<NameValuePair> userInfo = new ArrayList<NameValuePair>();
            userInfo.add(new BasicNameValuePair("name", name));

            jsonObject = jsonParser.makeHTTPRequest(Constants.URL_CREATE,Constants.METHOD_GET,userInfo);

            String reply = null;

            try{
                reply = jsonObject.getString("reply");

                if(reply.equals("done")){
                    isDone = true;
                    Constants.gameId = jsonObject.getString("gameid");
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(isDone){
                textViewStatus.setVisibility(View.VISIBLE);
                //game created redirect to game
                Constants.hostingGame = true;
                Constants.actionUserId = 1;
                //Log.d("gameid", Constants.gameId);
                //Log.d("hosting", Constants.hostingGame.toString());
                Intent in = new Intent(getApplicationContext(), GameActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }else {
                textViewStatus.setText("Something went wrong please restart the game!");
                textViewStatus.setVisibility(View.VISIBLE);
            }
        }


    }
}
