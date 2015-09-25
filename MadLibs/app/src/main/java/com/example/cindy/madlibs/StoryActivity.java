package com.example.cindy.madlibs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StoryActivity extends Activity {

    private Intent toNewStoryActivity;
    private TextToSpeech tts;
    private boolean readyToSpeak;
    private String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent toStoryActivity = getIntent();
        extra = toStoryActivity.getStringExtra("story");
        String story_name = toStoryActivity.getStringExtra("story_name");

        TextView story_text = (TextView) findViewById(R.id.story_text);
        story_text.setText(extra);

        TextView your_story_text = (TextView) findViewById(R.id.your_story_text);
        your_story_text.setText("Your Mad Lib Story:   " + story_name + "!");



        readyToSpeak = false;
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                readyToSpeak = true;
                //code to run when done loading
                tts.speak(extra , TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        toNewStoryActivity = new Intent(this, ChooseStoryActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickNewStory(View view) {
        startActivity(toNewStoryActivity);
    }

    public void onClickSpeakStory(View view) {
        if(readyToSpeak) {
            tts.speak(extra , TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}


