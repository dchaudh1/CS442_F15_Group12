package group12.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity {
    public ArrayList<String> itemName;
    public ArrayList<String> itemPrice;
    public ArrayList<String> itemQty;
    DB myDB;
    ListView listView;
    ListDataAdapter listDataAdapter;
    public static ListView menuListView; //Added by Deepika
    d_cartDB db; //Added by Deepika


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            db = new d_cartDB(this); //Added by Deepika
            menuListView = (ListView) findViewById(R.id.menuListView);  //Added by Deepika
            listView = (ListView) findViewById(R.id.menuListView);
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

            Button addToCartBtn = (Button) findViewById(R.id.addToCartBtn);
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToCartBtnClk(view);
                }
            });
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

    // Added by Deepika
    private void addToCartBtnClk(View view) {
        getMenuList();
        emptyCart(view);
        addToCart();
        Intent intent = new Intent(this, d_MenuCartActivity.class);
        startActivity(intent);
    }

    // Added by Deepika
    public void getMenuList() {
        itemName = new ArrayList<String>();
        itemPrice = new ArrayList<String>();
        itemQty = new ArrayList<String>();
//        itemName = null;
//        itemPrice = null;
//        itemQty = null;
        View parentView = null;
        Random r = new Random();
        for(int i=0; i < MainActivity.menuListView.getCount(); i++) {
            parentView = getViewByPostion(i, MainActivity.menuListView);
            itemName.add(((TextView) parentView.findViewById(R.id.t_name)).getText().toString());
            itemPrice.add(((TextView) parentView.findViewById(R.id.t_price)).getText().toString());
//            Deepika: Cannot fetch values until Quantity field is functional in Smruti's code.
//            itemQty.add(((TextView) parentView.findViewById(R.id.t_qty)).getText().toString());
            int tmpQty = r.nextInt(9); //Temporay hardcoding of Quantity values.
            itemQty.add(Integer.toString(tmpQty));
        }
    }

    // Added by Deepika
    public View getViewByPostion(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        }
        else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    //Added by Deepika
    public void addToCart() {
        Iterator itr1 = itemName.iterator();
        Iterator itr2 = itemPrice.iterator();
        Iterator itr3 = itemQty.iterator();
        while(itr1.hasNext() && itr2.hasNext() && itr3.hasNext()) {
            String itemNameStr = itr1.next().toString();
            String itemPriceStr = itr2.next().toString();
            String itemQtyStr = itr3.next().toString();
            double itemPriceNum = Double.parseDouble(itemPriceStr);
            int itemQtyNum = Integer.parseInt(itemQtyStr);
            double itemTotalPrice = itemPriceNum * itemQtyNum;
            db.insertData(itemNameStr, itemPriceStr, itemQtyStr, Double.toString(itemTotalPrice));
        }
    }

    //Added by Deepika
    public void emptyCart(View view) {
        db.truncateDB();
        d_MenuCartActivity.global_finalTotal = 0.0;
        d_MenuCartActivity.global_qty = 0;
    }
}
