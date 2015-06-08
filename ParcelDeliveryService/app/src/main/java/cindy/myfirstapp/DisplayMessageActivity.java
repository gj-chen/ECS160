package cindy.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import java.util.Objects;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.widget.Toast;


public class DisplayMessageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the display_message (knapsack) as the activity layout
        setContentView(R.layout.activity_display_message);

        //retrieve the object
        ListView knapsackList = (ListView) findViewById(R.id.knapsack_List);
        System.out.println("knapsackList okay");

        //Add Parcel Button
        Button button;
        button = (Button)findViewById(R.id.add_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new FetchSQL().execute();
            }
        });

        //Button button2;
        //button2 = (Button)findViewById(R.id.remove_item);

        //button2.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View arg0) {
              //  EditText parcel_item = (EditText)findViewById(R.id.parcel);
                //String parcel = parcel_item.getText().toString();

                //for(int i = 0; i < knapsackItems.size(); i++) {
                  //  if(Objects.equals(parcel, knapsackItems.get(i)))
                    //    knapsackItems.remove(parcel);
                //}
               //listAdapter.notifyDataSetChanged();
                //System.out.println(knapsackItems);
           // }
        //});

    }

    //Add Parcel Button
    private class FetchSQL extends AsyncTask<Void,Void,String> {
        Context context = getApplicationContext();

        @Override
        protected String doInBackground(Void... params) {
            String retval = "";
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                retval = e.toString();
            }

            String url = "jdbc:postgresql://10.0.2.2/postgres?user=postgres&password=05258729";
            Connection connection = null;

            PreparedStatement statement_parcelinsert = null;

            //Receiving Bundle
            Bundle bundle = null;
            bundle = getIntent().getExtras();
            String username = bundle.getString("username");
            String password = bundle.getString("password");
            System.out.println("after bundle extraction");

            //System.out.println(username);
            //System.out.println(password);


            //SQL commands
            String selection = "SELECT username, password FROM users WHERE username = '"+username+"' AND password = '"+password+"'";
            String add_parcel = "INSERT INTO parcels VALUES(?, ?)";
            //String check = "SELECT * FROM users WHERE username = '"+username+"' AND password = '"+password+"' ";
            //String add_parcel = "INSERT INTO users(knapsack) VALUES(?)";
            //String add_parcel = "INSERT INTO users(knapsack) VALUES(?) SELECT * FROM users WHERE username = '"+username+"' AND password = '"+password+"'";


            EditText parcel_item = (EditText)findViewById(R.id.parcel);
            String parcel = parcel_item.getText().toString();
            System.out.println("parcel okay");

            try {
                DriverManager.setLoginTimeout(5);
                connection = DriverManager.getConnection(url);

                statement_parcelinsert = connection.prepareStatement(add_parcel);
                Statement statement_check = connection.createStatement();

                System.out.println("connection made");

                statement_parcelinsert.setString(1, String.valueOf(username));
                statement_parcelinsert.setString(2, parcel);

                ResultSet rs = statement_check.executeQuery(selection);


               while(rs.next()){
                    System.out.println("inside if");
                    int insertion = statement_parcelinsert.executeUpdate();
                    System.out.println("insertion successful");
                    runToast();
                }

                statement_parcelinsert.close();
                statement_check.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                retval = e.toString();
                System.out.println(retval);
            }
            return retval;
        }
        @Override
        protected void onPostExecute(String value) {

        }
    }
    private void runToast() {
        runOnUiThread (new Thread(new Runnable() {
            public void run() {
                try {
                    Context context = getApplicationContext();
                    CharSequence text = "Parcel has been added!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));
    }


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
