package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.db.xiaoshiji.R;

/**
 * Created by Jay on 15-6-3.
 */
public class DishListAdpater extends BaseAdapter {
    @Override
    public int getCount() {
        return 30;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //TODO:图片设置
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = null;
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            switch (position % 3) {
                case 0:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishes_item_hasthreesmall, null);
                    myViewHolder = new MyViewHolder(view, 0);
                    break;
                case 1:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishes_item_hasbig, null);
                    myViewHolder = new MyViewHolder(view, 1);
                    break;
                case 2:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dishes_item_hastwo, null);
                    myViewHolder = new MyViewHolder(view, 2);
                    break;
            }
            view.setTag(myViewHolder);
        } else {
            view = convertView;
            myViewHolder = (MyViewHolder) view.getTag();
        }
        return view;
    }

    public static class MyViewHolder {
        int Type;
        ImageView imageView1, imageView2, imageView3;

        public MyViewHolder(View view, int i) {
            Type = i;
            switch (i) {
                case 0:
                    imageView1 = (ImageView) view.findViewById(R.id.iv_1);
                    imageView2 = (ImageView) view.findViewById(R.id.iv_2);
                    imageView3 = (ImageView) view.findViewById(R.id.iv_3);

                    break;
                case 1:
                    imageView1 = (ImageView) view.findViewById(R.id.iv_1);
                    imageView2 = (ImageView) view.findViewById(R.id.iv_2);
                    imageView3 = (ImageView) view.findViewById(R.id.iv_3);
                    break;
                case 2:
                    imageView1 = (ImageView) view.findViewById(R.id.iv_1);
                    imageView2 = (ImageView) view.findViewById(R.id.iv_2);
                    break;
            }
        }
    }
}

