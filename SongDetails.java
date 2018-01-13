
package com.example.blake.api_tutorial;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import model.Event;


public class SongDetails extends AppCompatActivity{

    private Event event;
    private TextView artist;
    private TextView song;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        //Get serialized object
        event = (Event) getIntent().getSerializableExtra("eventObj");



        artist = (TextView) findViewById(R.id.detsArtist);
        song = (TextView) findViewById(R.id.detsSong);





        artist.setText( event.getVenueName());
        song.setText(event.getSong());




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_event_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_websiteId) {

            String url = event.getWebsite();

            if (!url.equals("")) {
                Intent i =  new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(), "Website not available", Toast.LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }


}

