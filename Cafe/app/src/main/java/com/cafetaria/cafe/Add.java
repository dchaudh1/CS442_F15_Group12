package com.cafetaria.cafe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Smruti on 10/20/2015.
 */
public class Add extends Activity {
    DB myDB;
    EditText editName,editPrice,editDesc;
    Button btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
         myDB = new DB(this);

        editName = (EditText)findViewById(R.id.eName);
        editPrice = (EditText)findViewById(R.id.ePrice);
        editDesc = (EditText)findViewById(R.id.eDesc);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        BtnAdd();
    }

    public void BtnAdd()
    {
      btnAdd.setOnClickListener(
              new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      boolean check = myDB.InsertValues(editName.getText().toString(), Integer.parseInt(editPrice.getText().toString()), editDesc.getText().toString());
                      if (check == true)
                          Toast.makeText(Add.this, "Item Added", Toast.LENGTH_LONG).show();
                      else
                          Toast.makeText(Add.this, "Item not added", Toast.LENGTH_LONG).show();

                      Intent in = new Intent(getBaseContext(), MainActivity.class);
                      startActivity(in);
                  }
              }

      );
    }
}

