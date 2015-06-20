package adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.DiningRoomInfoActivity;

import java.util.ArrayList;

import beans.DiningRoomInfo;

/**
 * Created by db on 6/1/15.
 */
public class DiningRoomListAdapter extends BaseAdapter {

    public Context context;
//    public ArrayList<DiningRoomInfo> diningRoomInfos;
    public ArrayList<String> names;
    public ArrayList<String> addresses;

    public DiningRoomListAdapter(Context context){
        super();
        this.context = context;
//        this.diningRoomInfos = diningRoomInfos;
        init();
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.diningroom_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.logo = (ImageView)view.findViewById(R.id.diningroom_logo);
            viewHolder.address = (TextView)view.findViewById(R.id.address);
            viewHolder.name = (TextView)view.findViewById(R.id.textview_name);
            viewHolder.rank = (TextView)view.findViewById(R.id.textview_rank);
            viewHolder.details = (TextView)view.findViewById(R.id.diningroom_details);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.name.setText(names.get(i));
        viewHolder.address.setText(addresses.get(i));

        final int temp = i;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context,DiningRoomInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("diningroomname",names.get(temp));
                bundle.putString("diningroomaddress",addresses.get(temp));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        return view;
    }

    public static class ViewHolder{
        ImageView logo;
        TextView name,rank,details,address;
    }
    public void init(){
        names = new ArrayList<String>();
        addresses = new ArrayList<String>();
        names.add("东教工");
        names.add("东篱");
        names.add("东园食堂一楼");
        names.add("东园食堂三楼");
        names.add("韵苑食堂一楼");
        names.add("东一食堂");
        addresses.add("华科东校区大门附近(近东校区医院)");
        addresses.add("华科东校区大门附近(近东校区医院)");
        addresses.add("华科洪山校区城建学院东");
        addresses.add("华科洪山校区城建学院东");
        addresses.add("华科东校区操场旁");
        addresses.add("华科主校区中心操场旁");
    }
}
