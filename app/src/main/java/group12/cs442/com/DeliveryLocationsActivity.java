package group12.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import dchaudh1.cs442.com.R;

/**
 * Created by Deepika on 11/8/15.
 */

public class DeliveryLocationsActivity extends Activity {
    public static String global_delLoc;
    RadioGroup delLocGrp;
    int ch;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverylocations);
        ch = -1;

            delLocGrp = (RadioGroup) findViewById(R.id.delLocations);
            delLocGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                    global_delLoc = checkedRadioButton.getText().toString();
                    View radioButton = radioGroup.findViewById(i);
                    ch = radioGroup.indexOfChild(radioButton);
                }
            });

        Button confDelLocBtn = (Button) findViewById(R.id.confDelLocBtn);
        confDelLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ch == -1)
                    Toast.makeText(DeliveryLocationsActivity.this, "Please choose a delivery location first", Toast.LENGTH_LONG).show();
                else
                    orderConfirmation(view);
            }
        });
    }

    public void orderConfirmation(View view){
        Intent intent = new Intent(this, OrderConfirmationActivity.class);
        startActivity(intent);
    }
}
