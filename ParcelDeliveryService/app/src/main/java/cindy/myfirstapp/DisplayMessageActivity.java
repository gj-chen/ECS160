package cindy.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class DisplayMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // get message from the intent
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
//        // create the text view
//        TextView textView = new TextView(this);
//        textView.setText("Hello, " + message +",");
//        textView.setText("Welcome to your knapsack!");

        // set the display_message as the activity layout
        setContentView(R.layout.activity_display_message);

        //retrieve the object
        ListView knapsackList = (ListView) findViewById(R.id.knapsack_List);
        // populate the data
        String[] knapsackItems = new String[]{"bike", "textbook", "pizza", "basketball"};
        // initialize the adapter
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, knapsackItems);
        knapsackList.setAdapter(listAdapter);

//        knapsackList.setOnItemClickListener(mMessageClickedHandler);
        // callback function when knapsack item is clicked
        knapsackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // go to new view with friends list
                setContentView(R.layout.activity_friends);
            }
        });
    }

//    // Create a message handling object as an anonymous class.
//    private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
//        public void onItemClick(AdapterView parent, View v, int position, long id) {
//            // Do something in response to the click
//
//        }
//    };

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
}
