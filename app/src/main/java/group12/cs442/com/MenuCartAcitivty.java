package group12.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import dchaudh1.cs442.com.R;

import static group12.cs442.com.Constant.*;

/**
 * Created by Deepika on 11/8/15.
 */

public class MenuCartAcitivty extends Activity
{
    public static double global_finalTotal;
    public static int global_qty;
    private ArrayList<HashMap> list;
    cartDB db;
    public static final String COL_1 ="ITEM";
    public static final String COL_2 ="PRICE";
    public static final String COL_3 ="QTY";
    public static final String COL_4 ="TOTAL";


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menucart);
        db = new cartDB(this);

        ListView lview = (ListView) findViewById(R.id.cartlistmain);
        populateList();
        cartListViewAdapter adapter = new cartListViewAdapter(this, list);
        lview.setAdapter(adapter);

        Button confirmOrderBtn = (Button) findViewById(R.id.confirmOrderBtn);
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Use to temporarily add values to cartDB
                Move this call to previous Screen */

//                addToCart("Bread", "10.2", "2", "20.5");

                confirmOrder(view);
            }
        });

        Button emptyCartBtn = (Button) findViewById(R.id.emptyCartBtn);
        emptyCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyCart(view);
            }
        });
    }

    private void populateList() {

        list = new ArrayList<HashMap>();

        HashMap header = new HashMap();
        header.put(FIRST_COLUMN,"ITEM NAME");
        header.put(SECOND_COLUMN, "PRICE");
        header.put(THIRD_COLUMN, "QTY");
        header.put(FOURTH_COLUMN, "COST");
        list.add(header);

        Cursor result = db.getData();
        if (result.getCount() == 0) {
            Toast.makeText(this, "Cart is Empty!", Toast.LENGTH_SHORT).show();
        }

        global_finalTotal = 0.0;

        if (result.moveToFirst()) {
            do {
                String item, price, qty, total;
                item = result.getString(result.getColumnIndex(COL_1));
                price = result.getString(result.getColumnIndex(COL_2));
                qty = result.getString(result.getColumnIndex(COL_3));
                total = result.getString(result.getColumnIndex(COL_4));
                global_finalTotal  += Double.parseDouble(total);
                global_qty += Integer.parseInt(qty);
                HashMap hm = new HashMap();
                hm.put(FIRST_COLUMN, item);
                hm.put(SECOND_COLUMN, "$"+price);
                hm.put(THIRD_COLUMN, qty);
                hm.put(FOURTH_COLUMN, "$"+total);
                list.add(hm);
            } while (result.moveToNext());
        }

        HashMap finalPrice = new HashMap();
        finalPrice.put(FIRST_COLUMN, "TOTAL COST:");
        finalPrice.put(SECOND_COLUMN, "");
        finalPrice.put(THIRD_COLUMN, "");
        finalPrice.put(FOURTH_COLUMN, "$" + global_finalTotal);
        list.add(finalPrice);
    }

    public void confirmOrder(View view){
        Intent intent = new Intent(this, DeliveryOptionActivity.class);
        startActivity(intent);
    }

    /* Probably Move this function to previous Activity */
    public void addToCart(String item, String price, String qty, String total) {

//        String itemName = ((EditText)findViewById(R.id.itemA)).getText().toString();
//        String itemPrice = ((EditText)findViewById(R.id.priceA)).getText().toString();
//        String itemDesc = ((EditText)findViewById(R.id.itemDescA)).getText().toString();

        db.insertData(item, price, qty, total);

        /* Switch to next Screen */
//        Intent in = new Intent(getBaseContext(), MenuCartAcitivty.class);
//        startActivity(in);
    }

    public void emptyCart(View view) {
        db.truncateDB();
        global_finalTotal = 0.0;
        global_qty = 0;
        populateList();
        Intent refresh = new Intent(this, MenuCartAcitivty.class);
        startActivity(refresh);
        this.finish();
    }
}