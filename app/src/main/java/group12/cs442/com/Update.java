package group12.cs442.com;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Smruti on 10/23/2015.
 */
public class Update extends Activity{

    DB myDB;
    ListView listView;
    UpdateAdapter u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(R.id.menuListView);
            u= new UpdateAdapter(getApplicationContext(), R.layout.display_row);
            listView.setAdapter(u);

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
                    u.add(dp);


                } while (result.moveToNext());
            }

            u.notifyDataSetChanged();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
