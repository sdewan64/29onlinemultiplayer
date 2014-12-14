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
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GameListActivity extends Activity {

    private TextView textViewStatus,textViewgame1,textViewgame2,textViewgame3;
    private Button buttonBack;

    private JSONObject jsonObject;
    private JsonParser jsonParser = new JsonParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        findViewsById();
        implementButton();

        new GetGameList().execute();

    }

    private void implementButton() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), AccountMenuActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                finish();
            }
        });
    }

    private void findViewsById() {

        textViewStatus = (TextView) findViewById(R.id.textviewStatusX);
        textViewgame1 = (TextView) findViewById(R.id.textViewgame1);
        textViewgame2 = (TextView) findViewById(R.id.textViewgame2);
        textViewgame3 = (TextView) findViewById(R.id.textViewgame3);
        buttonBack = (Button) findViewById(R.id.buttonBack);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_list, menu);
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

    private class GetGameList extends AsyncTask<String, String, String>{

        private boolean isDone = false;

        @Override
        protected String doInBackground(String... strings) {
            List<NameValuePair> emptyList = new ArrayList<NameValuePair>();

            jsonObject = jsonParser.makeHTTPRequest(Constants.URL_GAMELIST, Constants.METHOD_GET, emptyList);
            String reply = null;

            try {
                reply = jsonObject.getString("reply");

                if(reply.equals("nogamefound")){
                    textViewStatus.setText("No Online Game Available");
                }else if(reply.equals("gamefound")){

                    for(int i=0;i<3;i++){
                        String gameHost = jsonObject.getString("gameHost"+String.valueOf(i));
                        if(!gameHost.equals("null")){
                            final GameListAdapter nGame = new GameListAdapter(jsonObject.getString("gameHost"+String.valueOf(i)),jsonObject.getString("gameId"+String.valueOf(i)));

                            TextView showText = null;

                            switch (i){
                                case 0: showText = textViewgame1;
                                        break;
                                case 1: showText = textViewgame2;
                                        break;
                                case 2: showText = textViewgame3;
                                        break;
                            }

                            showText.setText(nGame.host);
                            showText.setVisibility(View.VISIBLE);

                            showText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Constants.joiningGame = true;
                                    Constants.gameId = nGame.gameId;

                                    Intent in = new Intent(getApplicationContext(),GameJoinerActivity.class);
                                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(in);
                                    finish();
                                }
                            });

                            //Log.d("reply",jsonObject.getString("gameHost"+String.valueOf(i)));
                            //Log.d("reply2",jsonObject.getString("gameId"+String.valueOf(i)));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
