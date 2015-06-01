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

/**
 * Created by db on 6/1/15.
 */
public class DiningRoomListAdapter extends BaseAdapter {

    public Context context;

    public DiningRoomListAdapter(Context context){
        super();
        this.context=context;
    }

    @Override
    public int getCount() {
        return 8;
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
            viewHolder.name = (TextView)view.findViewById(R.id.textview_name);
            viewHolder.rank = (TextView)view.findViewById(R.id.textview_rank);
            viewHolder.details = (TextView)view.findViewById(R.id.diningroom_details);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }

        return view;
    }

    public static class ViewHolder{
        ImageView logo;
        TextView name,rank,details;
    }
}
