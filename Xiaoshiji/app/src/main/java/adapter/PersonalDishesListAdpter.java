package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import java.util.ArrayList;

import beans.LostInfo;

/**
 * Created by Jay on 15-6-17.
 */
public class PersonalDishesListAdpter extends BaseAdapter{

    public Context context;

    public PersonalDishesListAdpter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
      return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View view=convertView;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.diningroom_list_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.logo.setImageResource(R.drawable._6);
        viewHolder.details.setText("查看详情");
        viewHolder.name.setText("糖醋里脊");
        viewHolder.address.setText("西一食堂");
        viewHolder.cost.setText("￥3.5/份");


        return view;
    }
    public static class ViewHolder{
        ImageView logo;
        TextView name,cost,details,address;
        ViewHolder(View view){
            logo= (ImageView)view.findViewById(R.id.diningroom_logo);
            address = (TextView)view.findViewById(R.id.address);
            name= (TextView)view.findViewById(R.id.textview_name);
            cost= (TextView)view.findViewById(R.id.textview_rank);
            details = (TextView)view.findViewById(R.id.diningroom_details);
        }
    }
}
