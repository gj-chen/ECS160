package cindy.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.widget.Toast;



public class RegisterPage extends ActionBarActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        addListenerOnButton();
        addListenerOnButton1();
    }

    private void addListenerOnButton() {
        final Context context = this;

        button = (Button)findViewById(R.id.login_id_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MyActivity.class);
                startActivity(intent);
            }

        });
    }

    private void addListenerOnButton1(){
        final Context context = this;

        button = (Button)findViewById(R.id.registration);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
               new FetchSQL().execute();
            }
        });
    }

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

            PreparedStatement statement_insert = null;

            EditText user = (EditText)findViewById(R.id.username);
            String username = user.getText().toString();
            EditText pass = (EditText)findViewById(R.id.password);
            String password = pass.getText().toString();

            //SQL commands
            String insert = "INSERT INTO users VALUES(?, ?)";
            String check = "SELECT * FROM users WHERE username = '"+username+"' AND password = '"+password+"' ";


            try {
                DriverManager.setLoginTimeout(5);
                connection = DriverManager.getConnection(url);

                statement_insert = connection.prepareStatement(insert);
                Statement statement_check = connection.createStatement();

                statement_insert.setString(1, username);
                statement_insert.setString(2, password);

                ResultSet rs = statement_check.executeQuery(check);

                //if user is in database, send to login page
                //else, insert into database (registration complete)

                if(rs.next()){
                    System.out.println("inside check");
                    //open toast: already registered user -> goes to login page
                    //Toast.makeText(getApplicationContext(),
                    //      "Registered User Already Exists", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MyActivity.class);
                    startActivity(intent);
                }
                else{
                    System.out.println("inside else - insert");
                    int insert_db = statement_insert.executeUpdate();
                    System.out.println("insert successful??");

                    //Toast.makeText(getApplicationContext(),
                      //      "Registration Successful! Please log in :)", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MyActivity.class);
                    startActivity(intent);
                }

                statement_insert.close();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_page, menu);
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

    public void sendMessage(View view) {

    }
}
