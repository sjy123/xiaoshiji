package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

/**
 * Created by Jay on 15-6-4.
 */
public class DishesListAdpter extends RecyclerView.Adapter<DishesListAdpter.MyHoler> {
    private Context context;
    private int[] imageLab;
    private int imageWidth;
    private LruCache<Integer,Bitmap> lruCache;
    public DishesListAdpter(Context context, int[] imageLab){
        this.context=context;
        this.imageLab=imageLab;
        int margin=converDpToPixel(40f);
        int width=((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
        imageWidth=(width-margin)/2;
        lruCache=new LruCache<Integer,Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }
    @Override
    public MyHoler onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.dishes_list_item,null);
        MyHoler myHoler=new MyHoler(view);
        return myHoler;
    }

    @Override
    public void onBindViewHolder(MyHoler myHoler, int i) {
        helpSetHeightAndBitmap(myHoler.imageView,i);
    }

    @Override
    public int getItemCount() {
        return imageLab.length;
    }

    public static class MyHoler extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public MyHoler(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_dishes);
            textView= (TextView) itemView.findViewById(R.id.tv_dishes);


        }
    }
    private int converDpToPixel(float dp) {
        Resources resources=context.getResources();
        DisplayMetrics metrics=resources.getDisplayMetrics();
        float px=dp * (metrics.densityDpi/160f);
        return (int) px;
    }
    public void helpSetHeightAndBitmap(ImageView imageView,int position){
        if (lruCache.get(position)==null){
            Log.v("sjy", "lrunot");

            ViewGroup.LayoutParams layoutParams =imageView.getLayoutParams();
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

            BitmapFactory.decodeResource(context.getResources(), imageLab[position], options);

        int height= (int) ((imageWidth*1.0*options.outHeight)/options.outWidth);
        options.inJustDecodeBounds=false;
        options.inSampleSize=options.outWidth/imageWidth;
        layoutParams.height=height;
        layoutParams.width=imageWidth;
        imageView.setLayoutParams(layoutParams);
            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), imageLab[position], options);
            imageView.setImageBitmap(bitmap);
            lruCache.put(position,bitmap);

    }
        else {
            Log.v("sjy", "lruhas");
            imageView.setImageBitmap(lruCache.get(position));
        }
    }
}
