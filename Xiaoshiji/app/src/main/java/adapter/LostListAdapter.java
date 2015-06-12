package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import java.util.ArrayList;

import beans.LostInfo;

/**
 * Created by db on 6/12/15.
 */
public class LostListAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<LostInfo> lostInfos;

    public LostListAdapter(Context context,ArrayList<LostInfo> lostInfos){
        super();
        this.context = context;
        this.lostInfos = lostInfos;
    }

    @Override
    public int getCount() {
        if (lostInfos.size()!=0){
            return lostInfos.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return lostInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.find_lost_item,null);
            viewHolder = new ViewHolder();
            viewHolder.mAddress = (TextView)convertView.findViewById(R.id.lost_address);
            viewHolder.mDate = (TextView)convertView.findViewById(R.id.lost_date);
            viewHolder.mContent = (TextView)convertView.findViewById(R.id.lost_contaent);
            viewHolder.mDetails = (TextView)convertView.findViewById(R.id.lost_details);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        LostInfo lostInfo = lostInfos.get(lostInfos.size()-position-1);

        viewHolder.mAddress.setText(lostInfo.getLostPlace());
        viewHolder.mDate.setText(lostInfo.getLostDate());
        viewHolder.mContent.setText(lostInfo.getLostName());

        viewHolder.mDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
    public static class ViewHolder{
        TextView mAddress,mDate,mContent,mDetails;
    }
}
