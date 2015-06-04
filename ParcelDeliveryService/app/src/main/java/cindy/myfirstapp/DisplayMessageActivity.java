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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class DisplayMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the display_message (knapsack) as the activity layout
        setContentView(R.layout.activity_display_message);
        //addListenerOnButton(); //listener for Add Parcel button

        //retrieve the object
        final ListView knapsackList = (ListView) findViewById(R.id.knapsack_List);

        // Populate Data
        //String[] knapsackItems = new String[]{"bike", "textbook", "pizza", "basketball"};
        final ArrayList<String> knapsackItems = new ArrayList<String>();

        knapsackItems.add("Bike");
        knapsackItems.add("Textbook");
        knapsackItems.add("Pizza");
        knapsackItems.add("Basketball");


        // initialize the adapter
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, knapsackItems);
        knapsackList.setAdapter(listAdapter);

        //knapsackList.setOnItemClickListener(mMessageClickedHandler);
        // callback function when knapsack item is clicked
       knapsackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // go to new view with friends list
                selectItem(view);
            }
        });

        Button button;
        button = (Button)findViewById(R.id.add_item);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText parcel_item = (EditText)findViewById(R.id.parcel);
                String parcel = parcel_item.getText().toString();
                System.out.println("hello kekeke");
                //on click, update list in knapsack
                //listAdapter = null;
                knapsackItems.add(parcel);
                //listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, knapsackItems);
                //knapsackList.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                System.out.println(knapsackItems);
            }
        });

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

    // called when the a knapsack item is clicked
    public void selectItem(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }
}
