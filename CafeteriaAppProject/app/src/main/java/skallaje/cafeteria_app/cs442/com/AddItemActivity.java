package skallaje.cafeteria_app.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddItemActivity extends Activity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    byte [] img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img = bos.toByteArray();

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean addNew(View v){

       // byte [] img;
        DatabaseFunctions dbf = new DatabaseFunctions(this);
        dbf.open();
        EditText item = (EditText) findViewById(R.id.item);
        EditText price = (EditText) findViewById(R.id.price);
        EditText desc = (EditText) findViewById(R.id.desc);

        if(item.getText().toString().trim().length() == 0 ) {
            Toast.makeText(this, "ITEM cannot be blank!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(price.getText().toString().trim().length() == 0 ) {
            Toast.makeText(this, "PRICE cannot be blank!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(isFloat(price.getText().toString().trim())== false){
            Toast.makeText(this, "PRICE must be a number!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(desc.getText().toString().trim().length() == 0 ) {
            Toast.makeText(this, "DESCRIPTION cannot be blank!", Toast.LENGTH_LONG).show();
            return false;
        }

        if(img == null){
            Toast.makeText(this, "Please upload an image!", Toast.LENGTH_LONG).show();
            return false;

        }

        MenuItems m = dbf.getFoodItem(-1, item.getText().toString());
        if(m==null) {

            dbf.createMenuItem(item.getText().toString(), price.getText().toString(), desc.getText().toString(),img);
        }
        else{
            Toast.makeText(this, "The item already exists. Do you wish to update? ", Toast.LENGTH_LONG).show();
            return false;
        }

        Intent original_intent = new Intent(this,MenuActivity.class);
        //Intent original_intent = new Intent(this,TestActivity.class);
        startActivity(original_intent);
        return true;


    }

    public  boolean isFloat (String amount){
        boolean isValid = true;
        try {
            Float.parseFloat(amount);
        }
        catch(NumberFormatException e){
            isValid = false;
        }
        return isValid;
    }
}
