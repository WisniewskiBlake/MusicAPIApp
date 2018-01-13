
package com.example.blake.api_tutorial;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Util.Prefs;
import data.CustomListviewAdapter;
import model.Event;

public class MainActivity extends AppCompatActivity {

    private CustomListviewAdapter adapter;
    private ArrayList<Event> events = new ArrayList<>();
    private ListView listView;
    private TextView selectedCity;
    private ProgressDialog pDialog;

    private final static String URL_LastFM = "https://post.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=3b89f70251cbd87fbe2b9143c418f0c9&format=json";

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        getSongs(URL_LastFM);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListviewAdapter(MainActivity.this, R.layout.list_row, events);
        listView.setAdapter(adapter);

        Prefs prefs = new Prefs(MainActivity.this);
        String city = prefs.getCity();
        selectedCity = (TextView) findViewById(R.id.selectedLocationText);
        selectedCity.setText("Top Songs: ");

    }

    private void getSongs(String url){
        events.clear();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_LastFM, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject tracks = response.getJSONObject("tracks");

                    JSONArray track = tracks.getJSONArray("track");

                    for (int i = 0; i < track.length(); i++) {

                        JSONObject propertiesObj = track.getJSONObject(i);
                        JSONObject artist = propertiesObj.getJSONObject("artist");

                        JSONArray image = propertiesObj.getJSONArray("image");
                        JSONObject imageText = image.getJSONObject(0);


                        String text = imageText.getString("#text");
                        String artistName = artist.getString("name");
                        String songName = propertiesObj.getString("name");

                        Event event = new Event();
                        event.setUrl(text);
                        event.setArtist(artistName);
                        event.setSong(songName);

                        Log.d("Artist: ", artistName);
                        Log.d("Song: ", songName);
//                        Log.d("Image: ", image.getString(0));

                        events.add(event);

                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error54", error.getMessage());
            }

        });
        //AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        queue.add(jsonObjectRequest);


    }

}
