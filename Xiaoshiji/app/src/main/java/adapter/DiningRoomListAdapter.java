package adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import java.util.ArrayList;

import beans.DiningRoomInfo;

/**
 * Created by db on 6/1/15.
 */
public class DiningRoomListAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<DiningRoomInfo> diningRoomInfos;

    public DiningRoomListAdapter(Context context,ArrayList<DiningRoomInfo> diningRoomInfos){
        super();
        this.context = context;
        this.diningRoomInfos = diningRoomInfos;
    }

    @Override
    public int getCount() {
        if (diningRoomInfos!=null){
            return diningRoomInfos.size();

        }else {
            return 0;
        }
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

        viewHolder.name.setText(diningRoomInfos.get(i).getTitle());
        viewHolder.address.setText(diningRoomInfos.get(i).getAddress());

        return view;
    }

    public static class ViewHolder{
        ImageView logo;
        TextView name,rank,details,address;
    }
}
