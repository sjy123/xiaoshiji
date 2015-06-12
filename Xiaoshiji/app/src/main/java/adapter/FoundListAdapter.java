package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import java.util.ArrayList;

import beans.FoundInfo;
import beans.LostInfo;

/**
 * Created by db on 6/12/15.
 */
public class FoundListAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<FoundInfo> foundInfos;

    public FoundListAdapter(Context context,ArrayList<FoundInfo> foundInfos){
        super();
        this.context = context;
        this.foundInfos = foundInfos;
    }

    @Override
    public int getCount() {
        if (foundInfos.size()!=0){
            return foundInfos.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return foundInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.find_found_item,null);
            viewHolder = new ViewHolder();
            viewHolder.mAddress = (TextView)convertView.findViewById(R.id.found_address);
            viewHolder.mDate = (TextView)convertView.findViewById(R.id.found_date);
            viewHolder.mContent = (TextView)convertView.findViewById(R.id.found_contaent);
            viewHolder.mDetails = (TextView)convertView.findViewById(R.id.found_details);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        FoundInfo foundInfo = foundInfos.get(foundInfos.size()-position-1);

        viewHolder.mAddress.setText(foundInfo.getFoundPlace());
        viewHolder.mDate.setText(foundInfo.getFoundDate());
        viewHolder.mContent.setText(foundInfo.getFoundName());

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
