package adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.example.db.xiaoshiji.R;

import java.util.ArrayList;
import java.util.List;

import beans.BringMealInfo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by db on 6/1/15.
 */
public class FindListAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<BringMealInfo> bringMealInfos;

    public FindListAdapter(Context context,ArrayList<BringMealInfo> bringMealInfos){
        super();
        this.context = context;
        this.bringMealInfos = bringMealInfos;
    }

    @Override
    public int getCount() {
        if (bringMealInfos!=null){
            return bringMealInfos.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return bringMealInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return bringMealInfos.size()-i-1;
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

//        AVUser avUser = bringMealInfos.get(bringMealInfos.size()-i-1).getAVUser();
//        AVQuery<AVUser> query = AVUser.getQuery();
//        query.whereEqualTo("avuser",avUser);
//        query.findInBackground(new FindCallback<AVUser>() {
//            @Override
//            public void done(List<AVUser> avUsers, AVException e) {
//                if (e==null){
//                    Log.v("dbdb",avUsers.get(0).getUsername());
//                }
//            }
//        });

        viewHolder.logo.setText(bringMealInfos.get(bringMealInfos.size()-i-1).getMealname());
        viewHolder.name.setText(bringMealInfos.get(bringMealInfos.size()-i-1).getMealtype());
        viewHolder.address.setText(bringMealInfos.get(bringMealInfos.size()-i-1).getDestination());

        return view;
    }
    public static class ViewHolder{
        TextView logo;
        TextView name,address,details;
        ImageView person;

    }
}
