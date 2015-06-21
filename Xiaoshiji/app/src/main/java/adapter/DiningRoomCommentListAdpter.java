package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import java.util.Collections;
import java.util.List;

import beans.DiningRoomCommentInfo;


/**
 * Created by Jay on 15-6-1.
 */
public class DiningRoomCommentListAdpter extends BaseAdapter {
    List<DiningRoomCommentInfo> list;
    public DiningRoomCommentListAdpter(List list){
        Collections.reverse(list);
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.diningroom_comment_list_item,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_comment= (TextView) convertView.findViewById(R.id.textview_info);
            viewHolder.date= (TextView) convertView.findViewById(R.id.textview_date);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.date.setText(list.get(position).getDate());
        viewHolder.tv_comment.setText(list.get(position).getContent());
        return convertView;
    }

    public static class ViewHolder{
        TextView tv_comment,date;

    }
}
