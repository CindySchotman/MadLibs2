package com.example.cindy.madlibs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ChooseStoryActivity extends Activity {

    private Intent toFillWords;
    private String story_name;
    private TextView chosenStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_story);

        toFillWords = new Intent(this, FillWordsActivity.class);
        story_name = "random";

        chosenStory = (TextView) findViewById(R.id.chosenStory);
        chosenStory.setText("Story selected:  none");

        ListView storyList = (ListView) findViewById(R.id.storyListView);
        storyList.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> storyList, View row, int index, long rowID) {
                    // code to run when user clicks that item

                    switch (index) {
                        case 0:
                            story_name = "simple";
                            chosenStory.setText("Story selected:  simple");
                            break;
                        case 1:
                            story_name = "tarzan";
                            chosenStory.setText("Story selected:  tarzan");
                            break;
                        case 2:
                            story_name = "university";
                            chosenStory.setText("Story selected:  university");
                            break;
                        case 3:
                            story_name = "clothes";
                            chosenStory.setText("Story selected:  clothes");
                            break;
                        case 4:
                            story_name = "dance";
                            chosenStory.setText("Story selected:  dance");
                            break;
                        default:
                            story_name = "simple";
                            break;
                    }

                }
            }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_story, menu);
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


    public void toFillWordsActivity(View view) {

        toFillWords.putExtra("story_name", story_name);
        startActivity(toFillWords);
    }

}