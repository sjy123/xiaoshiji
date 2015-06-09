package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

/**
 * Created by Jay on 15-6-5.
 */
public class DishCommentAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View view=convertView;
        if (view==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_comment_item,null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        return view;
    }
    public static class ViewHolder{
        TextView tv_dishComment,tv_Date;
        public ViewHolder(View view){
            tv_dishComment = (TextView) view.findViewById(R.id.tv_dishComment);
            tv_Date = (TextView) view.findViewById(R.id.tv_dishDate);
        }
    }
}
