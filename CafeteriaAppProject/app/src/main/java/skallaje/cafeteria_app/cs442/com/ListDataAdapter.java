package skallaje.cafeteria_app.cs442.com;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smruti on 10/21/2015.
 */
public class ListDataAdapter extends ArrayAdapter {

    List list = new ArrayList();
    public ListDataAdapter(Context context, int resources)
    {
        super(context,resources);
    }

    static class  LayoutManager
    {
          TextView id,Name,Price,Desc;
    }

    @Override
    public void add(Object object)
    {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public  View getView(int position,View contentView,ViewGroup parent)
    {
        View row = contentView;
        LayoutManager lm;

        if( row == null)
        {
            LayoutInflater lf = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = lf.inflate(R.layout.display_row,parent,false);
            lm = new LayoutManager();
            lm.id= (TextView)row.findViewById(R.id.t_id);
            lm.Name= (TextView)row.findViewById(R.id.t_name);
            lm.Price = (TextView)row.findViewById(R.id.t_price);
          //  lm.Desc = (TextView)row.findViewById(R.id.t_desc);
            row.setTag(lm);
        }
        else
        {
            lm = (LayoutManager)row.getTag();
        }

        //final DataProvider dataProvider = (DataProvider)this.getItem(position);

      //final MenuItems m = (MenuItems)this.getItem(position);


        final MenuItems m = (MenuItems) this.getItem(position);

        lm.id.setText(String.valueOf(position + 1));
        lm.Name.setText(m.getItem());
        lm.Price.setText('$'+String.valueOf(m.getPrice()));
        //lm.Desc.setText(m.getDesc());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = String.valueOf(m.getID());
              /*  String Name = String.valueOf(m.getItem());
                String Price = String.valueOf(m.getPrice());
                String Desc = String.valueOf(m.getDesc()); */

                //DB db = new DB(getContext());

                Toast.makeText(getContext(), "Item Selected for Update", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), Update2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID", id);
                getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });

        return row;
    }

}
