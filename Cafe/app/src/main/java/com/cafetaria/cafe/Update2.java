package com.cafetaria.cafe;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Smruti on 10/23/2015.
 */
public class Update2 extends Activity{

    DB db;
    TextView etName,etPrice,etDesc;
    ImageView im;
    String id = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_update);
        id = getIntent().getExtras().getString("ID");
        db = new DB(this);
        Cursor res = db.getUpdateData(Integer.parseInt(id));

        etName = (TextView)findViewById(R.id.textView3);
        im = (ImageView)findViewById(R.id.imageView);

       // etPrice = (EditText)findViewById(R.id.ePrice);
       // etDesc = ( EditText)findViewById(R.id.eDesc);
        Button but = (Button)findViewById(R.id.btnUpdate);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  updateItem();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        if (res.getCount() == 0) {
            Toast.makeText(this, "No Data to show! ", Toast.LENGTH_LONG).show();
        }
        if (res.moveToFirst()) {
            do {
                String name, price, details;
                name = res.getString(1);
                price = res.getString(2);
                details = res.getString(3);
                etName.setText(details);
                int idd = getResources().getIdentifier("spate149_a6.cs442.com:mipmap/d" + String.valueOf(id), null, null);
                im.setImageResource(idd);
                //etPrice.setText(price);
               // etDesc.setText(details);
            } while (res.moveToNext());
        }
    }

   /* public void updateItem(){
        db = new DB(this);
        String name = etName.getText().toString();
        String price = etPrice.getText().toString();
        String details = etDesc.getText().toString();
        int res = db.updateItem(id,name,price,details);
        Toast.makeText(this, "Updated Item", Toast.LENGTH_LONG).show();
    }*/
}
