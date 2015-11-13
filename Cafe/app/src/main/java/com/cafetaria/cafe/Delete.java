package com.cafetaria.cafe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Smruti on 10/23/2015.
 */
public class Delete  extends AppCompatActivity {

    DB myDB;
    ListView listView;
    DeleteAdapter del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(R.id.listView);
            del= new DeleteAdapter(getApplicationContext(), R.layout.display_row);
            listView.setAdapter(del);

            myDB = new DB(this);

            Cursor result = myDB.getData();
            if (result.getCount() == 0) {
                Toast.makeText(this, "No Data stored in Table", Toast.LENGTH_LONG).show();
            }

            StringBuffer sb = new StringBuffer();


            if (result.moveToFirst()) {
                do {
                    String name, desc;
                    Integer id, price;

                    id = result.getInt(0);
                    name = result.getString(1);
                    price = result.getInt(2);
                    desc = result.getString(3);
                    DataProvider dp = new DataProvider(id, name, price, desc);
                    del.add(dp);


                } while (result.moveToNext());
            }

            del.notifyDataSetChanged();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
