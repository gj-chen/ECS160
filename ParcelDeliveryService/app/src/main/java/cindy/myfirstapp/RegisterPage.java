package cindy.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;



public class RegisterPage extends ActionBarActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        addListenerOnButton();
        new FetchSQL().execute();
    }

    private class FetchSQL extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params) {
            String sqltest = "";

            try{
                Class.forName("org.postgresql.Driver");
            }catch (ClassNotFoundException e){
                e.printStackTrace();
                sqltest = e.toString();
            }

            String url = "jdbc:postgresql://10.0.2.2/postgres?user=postgres&password=05258729";
            Connection conn;
            try{
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection(url);
                Statement st = conn.createStatement();
                String hello;
                hello = "SELECT 1";
                ResultSet rs = st.executeQuery(hello);
                while(rs.next()) {
                    sqltest = rs.getString(1);
                }
                rs.close();
                st.close();
                conn.close(); //close connection to database
            }catch(SQLException e){
                e.printStackTrace();
                sqltest = e.toString();
            }
return sqltest;
        }
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
