package com.example.cindy.madlibs;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class FillWordsActivity extends Activity {

    Story story;
    private TextView words_left;
    private TextView word_type;
    private EditText word_hint;
    private String word;
    private String type;
    private String spokenText;
    private boolean readyToSpeak;
    private TextToSpeech tts;

    private String story_name;
    private int n;

    private final int REQUEST_CODE_STT = 10;

    private Intent toStoryActivity;
    private InputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_words);

        Intent toFillWordsActivity = getIntent();
        story_name = toFillWordsActivity.getStringExtra("story_name");


        switch (story_name) {
            case "simple":
                n = 1; break;
            case "university":
                n = 2; break;
            case "tarzan":
                n = 3; break;
            case "clothes":
                n = 4; break;
            case "dance":
                n = 5; break;
            case "random":
                Random rand = new Random();
                n = rand.nextInt(5) + 1; break;
                //5 is the maximum and the 1 is our minimum
            default: n = 1;
        }


        switch (n) {
            case 1: stream = this.getResources().openRawResource(R.raw.madlib0_simple);
                break;
            case 2: stream = this.getResources().openRawResource(R.raw.madlib2_university);
                break;
            case 3: stream = this.getResources().openRawResource(R.raw.madlib1_tarzan);
                break;
            case 4: stream = this.getResources().openRawResource(R.raw.madlib3_clothes);
                break;
            case 5: stream = this.getResources().openRawResource(R.raw.madlib4_dance);
                break;
            default: stream = this.getResources().openRawResource(R.raw.madlib0_simple);
                break;
        }

        story = new Story();
        story.read(stream);


        int count = story.getPlaceholderRemainingCount();
        type = story.getNextPlaceholder();

        words_left = (TextView) findViewById(R.id.words_left);
        words_left.setText(count + " word(s) left");

        word_type = (TextView) findViewById(R.id.word_type);
        word_type.setText("please type a/an " + type);

        word_hint = (EditText) findViewById(R.id.word_hint);
        word_hint.setHint(type);

        toStoryActivity = new Intent(this, StoryActivity.class);


        readyToSpeak = false;
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                readyToSpeak = true;
                //code to run when done loading
                tts.speak("please type a/an " + type , TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fill_words, menu);
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

    public void fillWordActivity(View view) {

        word = String.valueOf(word_hint.getText());
        story.fillInPlaceholder(word.toUpperCase());

        if(!story.isFilledIn()) {
            int count = story.getPlaceholderRemainingCount();
            words_left.setText(count + " word(s) left");

            type = story.getNextPlaceholder();
            word_type.setText("please type a/an " + type);
            word_hint.setHint(type);
            word_hint.setText("");
        } else {
            String text_story = story.toString();
            toStoryActivity.putExtra("story", text_story);
            toStoryActivity.putExtra("story_name", story_name);
            startActivity(toStoryActivity);
        }

    }

    public void speakWordActivity(View view) {
        Intent intent = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //prompt text is shown on screen to tell user what to say
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "speak word out loud");

        try {
            startActivityForResult(intent, REQUEST_CODE_STT);
        } catch (ActivityNotFoundException anfe) {
            //code to handle the exception
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK && null != intent) {
            ArrayList<String> list = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            spokenText = list.get(0);
            word_hint.setText(spokenText);
        }
    }


}