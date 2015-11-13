package skallaje.cafeteria_app.cs442.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shara on 10/31/2015.
 */
public class DatabaseFunctions {

        // Database fields
        private SQLiteDatabase database;
        private CafeteriaDatabase dbHelper;
        private String[] login_allColumns = { CafeteriaDatabase.login_id,
                CafeteriaDatabase.password, CafeteriaDatabase.admin, CafeteriaDatabase.email, CafeteriaDatabase.email_sent };
        private String[] menu_allColumns = { CafeteriaDatabase.item_id,
            CafeteriaDatabase.item_name, CafeteriaDatabase.item_price, CafeteriaDatabase.item_desc, CafeteriaDatabase.item_image };

        public DatabaseFunctions(Context context) {
            dbHelper = new CafeteriaDatabase(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public User createID(String id, String pwd, String adm, String email, int sent) {
            ContentValues values = new ContentValues();
            values.put(CafeteriaDatabase.login_id, id);
            values.put(CafeteriaDatabase.password, pwd);
            values.put(CafeteriaDatabase.admin, adm);
            values.put(CafeteriaDatabase.email, email+"@hawk.iit.edu");
            values.put(CafeteriaDatabase.email_sent, sent);
            long insertId = database.insert(CafeteriaDatabase.LOGIN_TB, null,
                    values);
            Cursor cursor = database.query(CafeteriaDatabase.LOGIN_TB,
                    login_allColumns, CafeteriaDatabase.login_id + "= '" + id+"'", null,
                    null, null, null);
            cursor.moveToFirst();
            Log.d("App", "Count: " + cursor.getCount()+ " "+String.valueOf(insertId).trim());
            User u = link(cursor);
            cursor.close();
            return u;
        }

        public void deleteID(String id) {
            System.out.println("Comment deleted with id: " + id);
            database.delete(CafeteriaDatabase.LOGIN_TB, CafeteriaDatabase.login_id
                    + "= '" + id + "'", null);
        }

        public User getID(String id){
            Cursor cursor = database.query(CafeteriaDatabase.LOGIN_TB,
                    login_allColumns, CafeteriaDatabase.login_id + " = '" + id+"'", null,
                    null, null, null);
            cursor.moveToFirst();
            if(cursor.getCount() == 1) {
               Log.d("App", "Count: " + cursor.getCount());
               User u = link(cursor);
               cursor.close();
               return u;
            }
            cursor.close();
            return null;
        }

    public User checkIfRegistered(String email){
        Cursor cursor = database.query(CafeteriaDatabase.LOGIN_TB,
                login_allColumns, CafeteriaDatabase.email + " = '" + email+"'", null,
                null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount() == 1) {
            Log.d("App", "Count: " + cursor.getCount());
            User u = link(cursor);
            cursor.close();
            return u;
        }
        cursor.close();
        return null;
    }

        public void updateID(String id, String adm) {
            ContentValues values = new ContentValues();
            values.put(CafeteriaDatabase.admin, adm);
            database.update(CafeteriaDatabase.LOGIN_TB, values, CafeteriaDatabase.login_id + "= '" + id + "'", null);
        }

    public int updatePassword(String id, String pwd) {
        ContentValues values = new ContentValues();
        values.put(CafeteriaDatabase.admin, id);
        values.put(CafeteriaDatabase.password, pwd);
        int count = database.update(CafeteriaDatabase.LOGIN_TB, values, CafeteriaDatabase.login_id + "= '" + id + "'", null);
        return count;
    }

        public List<User> getAllUsers() {
            List<User> comments = new ArrayList<User>();

            Cursor cursor = database.query(CafeteriaDatabase.LOGIN_TB,
                    login_allColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User item = link(cursor);
                comments.add(item);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return comments;
        }

        private User link(Cursor cursor) {
            User u = new User(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3), Integer.valueOf(cursor.getString(4)));
            return u;
        }


    public MenuItems createMenuItem(String name, String price, String desc, byte[] img) {
        ContentValues values = new ContentValues();
        values.put(CafeteriaDatabase.item_name, name);
        values.put(CafeteriaDatabase.item_price, price);
        values.put(CafeteriaDatabase.item_desc, desc);
        values.put(CafeteriaDatabase.item_image, img);
        long insertId = database.insert(CafeteriaDatabase.MENU_TB, null,
                values);
        Cursor cursor = database.query(CafeteriaDatabase.MENU_TB,
                menu_allColumns, CafeteriaDatabase.item_id + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        MenuItems newitem = createMenuObject(cursor);
        cursor.close();
        return newitem;
    }

    public byte[] getImage(Integer id){
        //MenuItems m = getFoodItem(id,"");
        byte [] img = null;
        Cursor cursor = database.query(CafeteriaDatabase.MENU_TB,
                menu_allColumns, CafeteriaDatabase.item_id + " = " +id, null,
                null, null, null);

        if(cursor!=null) {
            cursor.moveToFirst();
            do {
                img = cursor.getBlob(cursor.getColumnIndex("image"));
            } while (cursor.moveToNext());
        }

        System.out.println("Comment deleted with img: " + img);
        return img;
    }

    public void deleteMenuItem(Integer id) {
        System.out.println("Comment deleted with id: " + id);
        database.delete(CafeteriaDatabase.MENU_TB, CafeteriaDatabase.item_id
                + " = " + id, null);
    }

    public MenuItems getFoodItem(Integer id, String item){
        Cursor cursor;
        if(item == "" && id != -1) {
            cursor = database.query(CafeteriaDatabase.MENU_TB,
                    menu_allColumns, CafeteriaDatabase.item_id + " = " + id, null,
                    null, null, null);
        }
        else {
            cursor = database.query(CafeteriaDatabase.MENU_TB,
                    menu_allColumns, CafeteriaDatabase.item_name + " = " + "'"+item+"'", null,
                    null, null, null);
        }

        if(cursor.getCount()==0){
            return null;
        }
        cursor.moveToFirst();
        MenuItems newitem = createMenuObject(cursor);
        cursor.close();
        return newitem;
    }

    public void updateFoodItem(Integer id, String name, String price, String desc) {
        ContentValues values = new ContentValues();
        values.put(CafeteriaDatabase.item_name, name);
        values.put(CafeteriaDatabase.item_price, price);
        values.put(CafeteriaDatabase.item_desc, desc);
        database.update(CafeteriaDatabase.MENU_TB, values, CafeteriaDatabase.item_id + "=" + id, null);
    }

    public List<MenuItems> getAllMenuItems() {
        List<MenuItems> ml = new ArrayList<MenuItems>();

        Cursor cursor = database.query(CafeteriaDatabase.MENU_TB,
                menu_allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Log.d("App","Vaaal "+new Integer(cursor.getString(0))+" "+cursor.getString(1)+" "+new Integer(cursor.getString(2)));
            MenuItems item = createMenuObject(cursor);
            ml.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return ml;
    }

    private MenuItems createMenuObject(Cursor cursor) {
        MenuItems m = new MenuItems(Integer.valueOf(cursor.getString(0)), cursor.getString(1),new Float(cursor.getString(2)), cursor.getString(3));
        return m;
    }
}

