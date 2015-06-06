package cindy.myfirstapp;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
//import android.support.v7.widget.SearchView;
import android.widget.SearchView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.Intent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;


public class MyActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.cindy.myfirst.app.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // default to login view
        addListenerOnButton();
    }

    Button button;

    public void addListenerOnButton() {
        final Context context = this;
        button = (Button)findViewById(R.id.register_button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, RegisterPage.class); // switch to register view
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /* called when the user clicks the Enter button */
    public void sendMessage(View view) {
        new FetchSQL().execute();

    }
    //After the user presses enter / login, we have to check if user is in database
    //if user is in database -> intent for friends page
    //else send to registration page

    private class FetchSQL extends AsyncTask<Void,Void,String> {
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

            EditText user = (EditText)findViewById(R.id.username);
            String username = user.getText().toString();
            EditText pass = (EditText)findViewById(R.id.password);
            String password = pass.getText().toString();

            //SQL commands
            String check = "SELECT * FROM users WHERE username = '"+username+"' AND password = '"+password+"' ";

            try {
                DriverManager.setLoginTimeout(5);
                connection = DriverManager.getConnection(url);

                Statement statement_check = connection.createStatement();

                ResultSet rs = statement_check.executeQuery(check);

                //check database for login info
                //if selection / login = TRUE, send to knapsack page
                // if selection / login = FALSE, open registration page

                if(rs.next()){
                    System.out.println("inside check");
                    //open toast: already registered user -> goes to login page
                    //Toast.makeText(getApplicationContext(),
                    //      "Registered User Already Exists", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), DisplayMessageActivity.class);
                    startActivity(intent);
                }
                else{
                    //Toast.makeText(getApplicationContext(),
                    //      "Registration Successful! Please log in :)", Toast.LENGTH_LONG).show();
                    System.out.println("inside else!!! wooooo!");
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), RegisterPage.class);
                    startActivity(intent);
                }

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



    public void openSettings() {
        //
    }

    public void openSearch() {
        //
    }



}
