package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by db on 6/1/15.
 */
public class FindListAdapter extends BaseAdapter {

    public Context context;

    public FindListAdapter(Context context){
        super();
        this.context = context;
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
            view= LayoutInflater.from(context).inflate(R.layout.find_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.logo = (TextView)view.findViewById(R.id.logo);
            viewHolder.person = (CircleImageView)view.findViewById(R.id.person);
            viewHolder.name = (TextView)view.findViewById(R.id.textview_name);
            viewHolder.address = (TextView)view.findViewById(R.id.address);
            viewHolder.details = (TextView)view.findViewById(R.id.diningroom_details);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        return view;
    }
    public static class ViewHolder{
        TextView logo;
        TextView name,address,details;
        ImageView person;

    }
}
