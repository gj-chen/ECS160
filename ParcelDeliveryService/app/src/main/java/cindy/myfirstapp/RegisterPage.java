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
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;



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
                //System.out.println("hi");
               new FetchSQL().execute();
            }
        });
    }

    TextView resultArea;
    private class FetchSQL extends AsyncTask<Void,Void,String> {
        @Override
        protected String doInBackground(Void... params) {

            System.out.println("hi");
            String retval = "";
            try {
                Class.forName("org.postgresql.Driver");
                System.out.println("driver is loaded");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                retval = e.toString();
                System.out.println("driver is not loaded");
            }
            String url = "jdbc:postgresql://10.0.2.2/postgres?user=postgres&password=05258729";
            //String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=05258729";
            Connection conn;
            String username = getString(R.string.prompt_username);
            String password = getString(R.string.prompt_password);
            System.out.println("hello");
            try {
                DriverManager.setLoginTimeout(5);
                System.out.println("Did not timeout T_T.");
                conn = DriverManager.getConnection(url);
                System.out.println("Made it into the database TYBG");
                Statement st = conn.createStatement();
                System.out.println("Before insert");
                String sql;
                sql = "INSERT INTO users(username) VALUES (?)";
                ResultSet rs = st.executeQuery(sql+username);
                System.out.println("after insert");
                while(rs.next()) {
                    retval = rs.getString(1);
                    System.out.print("Username = " + retval);
                }
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                retval = e.toString();
                System.out.println(retval);
            }
            return retval;
        }
        @Override
        protected void onPostExecute(String value) {
            //resultArea.setText(value);
            System.out.println("last hello");
        }
    }





    /*private class FetchSQL extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void... params) {
            /*String return_value = "";

            try{
                Class.forName(".org.postgresql.Driver");
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
                return_value = e.toString();
            } //throws & catches error for ClassNotFoundException (driver not found)

            String url = "jdbc:postgresql://10.0.2.2/postgres?user=postgres&password=05258729";
            Connection conn;
            String username = getString(R.string.prompt_username);
            String password = getString(R.string.prompt_password);

            try{
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection(url); //opens the database connection
                Statement stmt = conn.createStatement(); //creates an SQL statement call
                String sql;
                //if username & password != in database
                // insert into database
                sql = "SELECT * FROM userinformation WHERE username= "+username+" AND pw = "+password+"";
                ResultSet rs = stmt.executeQuery(sql);
                while(!rs.next()){
                    sql = "INSERT INTO userinformation(username, pw) VALUES('"+username+", "+password+"')";
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return_value = e.toString();
            }
        return return_value;
        }

    }*/

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
