package com.cafetaria.cafe;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    DB myDB;
    ListView listView;
    ListDataAdapter listDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(R.id.listView);
            listDataAdapter = new ListDataAdapter(getApplicationContext(), R.layout.display_row);
            listView.setAdapter(listDataAdapter);

            myDB = new DB(this);

            Cursor result = myDB.getData();
            if (result.getCount() == 0) {
                Toast.makeText(this, "No Data stored in Table", Toast.LENGTH_LONG).show();
            }

            if (result.moveToFirst()) {
                do {
                    String name, desc;
                    Integer id, price;

                    id = result.getInt(0);
                    name = result.getString(1);
                    price = result.getInt(2);
                    desc = result.getString(3);
                    DataProvider dp = new DataProvider(id, name, price, desc);
                    listDataAdapter.add(dp);


                } while (result.moveToNext());
            }

            listDataAdapter.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void doAdd(MenuItem item){
        Intent intent = new Intent(this, Add.class);
        startActivity(intent);
    }

    public void doDelete(MenuItem item)
    {
        Intent intent = new Intent(this, Delete.class);
        startActivity(intent);
    }

    public void doUpdate(MenuItem item){
        Intent intent = new Intent(this, Update.class);
        startActivity(intent);
    }


}
