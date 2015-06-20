package adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.DishesDetailActivity;

import java.util.ArrayList;
import java.util.List;

import beans.LostInfo;
import beans.PersonalDishesInfo;

/**
 * Created by Jay on 15-6-17.
 */
public class PersonalDishesListAdpter extends BaseAdapter{

    List<PersonalDishesInfo> list;
    public interface MyOnItemClickedListener{
         void onClick(String diningroomname,String mealname,String mealprice);
    }
    MyOnItemClickedListener myOnItemClickedListener=null;
    public void setMyOnItemClickedListener(MyOnItemClickedListener myOnItemClickedListener){
        this.myOnItemClickedListener=myOnItemClickedListener;
    }
    public PersonalDishesListAdpter( List<PersonalDishesInfo> list){
        this.list=list;
    }

    @Override
    public int getCount() {
      return list.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View view=convertView;
        if (view==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.diningroom_list_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.details.setText("查看详情");
        viewHolder.name.setText(list.get(position).getString("mealname"));
        viewHolder.address.setText(list.get(position).getString("diningroomname"));
        viewHolder.cost.setText("￥"+list.get(position).getString("mealprice"));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemClickedListener.onClick(list.get(position).getString("diningroomname"),list.get(position).getString("mealname"),list.get(position).getString("mealprice"));

            }
        });

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
