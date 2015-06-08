package cindy.myfirstapp;

import android.app.ListActivity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import java.security.AccessControlContext;
import java.util.ArrayList;
import java.util.Objects;


public class FriendsActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        //retrieve the object
        ListView friendsList = (ListView) findViewById(R.id.friends_List);
        // populate the data
        final ArrayList<String> friends = new ArrayList<String>();

        /*friends.add("Cindy");
        friends.add("Jason");
        friends.add("Gloglo");
        friends.add("Rogaway Senpai");
        */

        // initialize the adapter
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, friends);
        friendsList.setAdapter(listAdapter);

        //callback function when friend is clicked
        friendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendDelivery(view,id);
            }
        });

        Button button;
        button = (Button)findViewById(R.id.add_friend);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText friend_item = (EditText) findViewById(R.id.friend);
                String friend = friend_item.getText().toString();
                friends.add(friend);
                listAdapter.notifyDataSetChanged();
                System.out.println(friends);
                runToast();
            }
        });

        Button button2;
        button2 = (Button)findViewById(R.id.remove_friend);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                runToast1();
                EditText friend_item = (EditText) findViewById(R.id.friend);
                String friend = friend_item.getText().toString();

                for (int i = 0; i < friends.size(); i++) {
                    if (Objects.equals(friend, friends.get(i)))
                        friends.remove(friend);
                }
                listAdapter.notifyDataSetChanged();
                System.out.println(friends);
            }
        });

    }

    private void runToast() {
        runOnUiThread(new Thread(new Runnable() {
            public void run() {
                try {
                    Context context = getApplicationContext();
                    CharSequence text = "Friend has been added!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void runToast1() {
        runOnUiThread(new Thread(new Runnable() {
            public void run() {
                try {
                    Context context = getApplicationContext();
                    CharSequence text = "Friend has been removed!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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
    // called when the delivery button is clicked
    public void sendDelivery(View view, long id) {
        Intent intent = new Intent(this, DeliveryActivity.class);
        String deliverToFriend = Long.toString(id);
//        intent.putExtra(EXTRA_MESSAGE, deliverToFriend);
        startActivity(intent);

        /*open up google maps application instead of using MapsActivity! */
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?saddr=enter your location&daadr=enter your destination"));
//        startActivity(intent);
    }
}
