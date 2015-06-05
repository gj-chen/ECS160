package cindy.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class DeliveryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        final Context context = this;

        /* direct delivery option */
        Button direct_button;
        direct_button = (Button)findViewById(R.id.directDelivery);
        direct_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDelivery(view);
            }
        });
        /* indirect delivery option */
        Button indirect_button;
        indirect_button = (Button)findViewById(R.id.indirectDelivery);
        indirect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickIndirectFriend(view);
            }
        });

        /* successful delivery option */
        Button button;
        button = (Button)findViewById(R.id.deliverySuccess);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterPage.class); // switch to register view
                startActivity(intent);
            }
        });

        /* unsuccessful delivery option */
        Button button2;
        button2 = (Button)findViewById(R.id.deliveryNoSuccess);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegisterPage.class); // switch to register view
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delivery, menu);
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

    public void sendDelivery(View view) {
        /*open up google maps application instead of using MapsActivity! */
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?saddr=enter your location&daadr=enter your destination"));
        startActivity(intent);
    }

    public void pickIndirectFriend(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

}
