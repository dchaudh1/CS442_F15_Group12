package skallaje.cafeteria_app.cs442.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends Activity {
    ListView listView;
    UserSession s;
    MenuAdapter md;
    List<MenuItems> mlist;
    ListDataAdapter listDataAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseFunctions dbf = new DatabaseFunctions(this);
        dbf.open();

        mlist = dbf.getAllMenuItems() ;

        s=new UserSession(this);

       // md = new MenuAdapter(mlist, this);
       // listView.setAdapter(md);

        listView = (ListView) findViewById(R.id.listViews);
        listDataAdapter = new ListDataAdapter(getApplicationContext(), R.layout.display_row);
        listView.setAdapter(listDataAdapter);

            for(int i = 0;i<mlist.size();i++) {

                MenuItems m = new MenuItems(mlist.get(i).getID(),
                        mlist.get(i).getItem(),
                        mlist.get(i).getPrice(),
                        mlist.get(i).getDesc());
                listDataAdapter.add(m);
            }

        listDataAdapter.notifyDataSetChanged();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.menu_logout:
                if(s != null) {
                    s.deleteSession();
                    Intent logout = new Intent(this, LoginActivity.class);
                    startActivity(logout);
                }
                break;

            case R.id.menu_change_password:
                 if(s != null) {
                    Intent change_password = new Intent(this, ChangePasswordActivity.class);
                    startActivity(change_password);
                }
                break;
        }

        return true;
    }



}


