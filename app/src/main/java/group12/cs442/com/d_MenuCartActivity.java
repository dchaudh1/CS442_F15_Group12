package group12.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static group12.cs442.com.d_Constant.*;

/**
 * Created by Deepika on 11/8/15.
 */

public class d_MenuCartActivity extends Activity
{
    public static double global_finalTotal;
    public static int global_qty;
    private ArrayList<HashMap> list;
//    private ArrayList<String> itemName;
//    private ArrayList<String> itemPrice;
//    private ArrayList<String> itemQty;
    public static final String COL_1 ="ITEM";
    public static final String COL_2 ="PRICE";
    public static final String COL_3 ="QTY";
    public static final String COL_4 ="TOTAL";
//    ListView menuListView;
    d_cartDB db;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_menucart);
        db = new d_cartDB(this);

//        menuListView = (ListView) findViewById(R.id.menuListView);
//        getMenuList();

//        addToCart();

        ListView lview = (ListView) findViewById(R.id.cartlistmain);
        populateList();
        d_cartListViewAdapter adapter = new d_cartListViewAdapter(this, list);
        lview.setAdapter(adapter);

        Button confirmOrderBtn = (Button) findViewById(R.id.confirmOrderBtn);
        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* Use to temporarily add values to d_cartDB
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
        Intent intent = new Intent(this, d_DeliveryOptionActivity.class);
        startActivity(intent);
    }

    /* Probably Move this function to previous Activity */
    /*
    public void addToCart() {
        Iterator itr1 = MainActivity.itemName.iterator();
        Iterator itr2 = MainActivity.itemPrice.iterator();
        Iterator itr3 = MainActivity.itemQty.iterator();
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
    */

    public void emptyCart(View view) {
        db.truncateDB();
        global_finalTotal = 0.0;
        global_qty = 0;
        populateList();
        Intent refresh = new Intent(this, d_MenuCartActivity.class);
        startActivity(refresh);
        this.finish();
    }

    /*
    public void getMenuList() {
        itemName = null;
        itemPrice = null;
        itemQty = null;
        View parentView = null;
        for(int i=0; i < MainActivity.menuListView.getCount(); i++) {
            parentView = getViewByPostion(i, MainActivity.menuListView);
            itemName.add(((TextView) parentView.findViewById(R.id.t_name)).getText().toString());
            itemPrice.add(((TextView) parentView.findViewById(R.id.t_price)).getText().toString());
            itemQty.add(((TextView) parentView.findViewById(R.id.t_name)).getText().toString());
        }
    }

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
    */
}