package group12.cs442.com;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Random;

import dchaudh1.cs442.com.R;

/**
 * Created by Deepika on 11/8/15.
 */
public class OrderConfirmationActivity extends Activity{
    String delLoc;
    Double finalTotal;
    int cartItemsQty;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderconfirm);

        TextView a = (TextView) findViewById(R.id.a);
        TextView b = (TextView) findViewById(R.id.b);
        TextView c = (TextView) findViewById(R.id.c);
        TextView d = (TextView) findViewById(R.id.d);

        Random r = new Random();
        int orderNo = r.nextInt(999999);

        /* Get Delivery Option (Pickup/Delivery) */
        getDeliveryOption();
        getFinalCartValue();
        getCartItemsQty();

        d.setText("Order#: " +orderNo);
        a.setText("Total Number of Items in Cart: " +cartItemsQty);
        b.setText("Total Amount Payable: " +finalTotal);
        c.setText("Delivery Method: " +delLoc);
    }

    private void getDeliveryOption() {
        int delOpt = DeliveryOptionActivity.global_delOpt;
        if(delOpt == 0) {
            delLoc = "Pickup at MTCC";
        }
        if(delOpt == 1) {
            getDeliveryLocation();
        }
    }

    private void  getDeliveryLocation() {
        delLoc = DeliveryLocationsActivity.global_delLoc;
    }

    private void getFinalCartValue() {
        finalTotal = MenuCartAcitivty.global_finalTotal;
    }

    private void getCartItemsQty() {
        cartItemsQty = MenuCartAcitivty.global_qty;
    }
}
