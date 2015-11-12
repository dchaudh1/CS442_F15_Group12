package group12.cs442.com;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smruti on 10/23/2015.
 */
public class DeleteAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public DeleteAdapter(Context context, int resources)
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
    public View getView(final int position,View contentView,ViewGroup parent)
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
            lm.Desc = (TextView)row.findViewById(R.id.t_desc);
            row.setTag(lm);
        }
        else
        {
            lm = (LayoutManager)row.getTag();

        }

        final DataProvider dataProvider = (DataProvider)this.getItem(position);

        lm.id.setText(dataProvider.getID().toString());
        lm.Name.setText(dataProvider.getName().toString());
        lm.Price.setText(dataProvider.getPrice().toString());
        lm.Desc.setText(dataProvider.getDesc().toString());

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = String.valueOf(dataProvider.getID());
                DB db = new DB(getContext());
                db.deleteItem(id);
                list.remove(position);
                Toast.makeText(getContext(), "Item Deleted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });
        return row;

}
}
