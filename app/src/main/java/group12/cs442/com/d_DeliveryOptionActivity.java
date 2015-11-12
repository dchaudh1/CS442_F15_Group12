package group12.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Deepika on 11/8/15.
 */
public class d_DeliveryOptionActivity extends Activity{
    public static int global_delOpt;
    RadioGroup delOptGrp;
    int ch;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d_deliveryoptions);
        ch = -1;

        delOptGrp = (RadioGroup)findViewById(R.id.delChoice);
        delOptGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                ch = radioGroup.indexOfChild(radioButton);
            }
        });

        Button proceedBtn = (Button) findViewById(R.id.proceedBtn);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                global_delOpt = ch;
                if(ch==0)
                    orderConfirmation(view);
                else if(ch==1)
                    deliveryLocations(view);
                else
                    Toast.makeText(d_DeliveryOptionActivity.this, "Please choose a delivery option first", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void orderConfirmation(View view){
        Intent intent = new Intent(this, d_OrderConfirmationActivity.class);
        startActivity(intent);
    }

    public void deliveryLocations(View view){
        Intent intent = new Intent(this, d_DeliveryLocationsActivity.class);
        startActivity(intent);
    }
}
