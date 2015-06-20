package adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;

import java.util.ArrayList;
import java.util.Random;

import beans.DishMenuInfo;


/**
 * Created by Jay on 15-6-4.
 */
public class DishesListAdpter extends RecyclerView.Adapter<DishesListAdpter.MyHoler> {

    private Context context;
    private int[] imageLab;
    private int imageWidth;

    /*
    db 菜谱惹~
     */
    public ArrayList<DishMenuInfo> dishMenuInfos;

    private LruCache<Integer,Bitmap> lruCache;
    public interface MyOnItemClickedListener{
        public void onClick(String mealname,String mealprice);
      //  public void onClick(dishMenuInfos.get(myHoler.position).getMealName()+"  "+dishMenuInfos.get(myHoler.position).getMealPrice()+"/份");
    }
    MyOnItemClickedListener myOnItemClickedListener=null;
    public void setMyOnItemClickedListener(MyOnItemClickedListener myOnItemClickedListener){
        this.myOnItemClickedListener=myOnItemClickedListener;
    }
    public DishesListAdpter(Context context, int[] imageLab,ArrayList<DishMenuInfo> dishMenuInfos){
        this.context=context;
        this.imageLab=imageLab;
        this.dishMenuInfos = dishMenuInfos;
        int margin=converDpToPixel(20);
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
        View view= LayoutInflater.from(context).inflate(R.layout.dishes_list_item, null);
        //设置dishesListItem点击进入菜品详情

        MyHoler myHoler=new MyHoler(view,i);
        ViewGroup.LayoutParams layoutParams1=myHoler.textView.getLayoutParams();
        layoutParams1.width = imageWidth;
        myHoler.textView.setLayoutParams(layoutParams1);
        return myHoler;
    }

    @Override
    public void onBindViewHolder(MyHoler myHoler, int i) {
        myHoler.position=i;

        //提前确定view高度
        BitmapFactory.Options optionsView=new BitmapFactory.Options();
        optionsView.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),imageLab[i%8],optionsView);
        ViewGroup.LayoutParams layoutParams=myHoler.imageView.getLayoutParams();
        int viewHeight= (int) (imageWidth*(optionsView.outWidth*1.0/optionsView.outHeight*1.0));
        layoutParams.height=viewHeight;
        layoutParams.width=imageWidth;
        myHoler.imageView.setLayoutParams(layoutParams);
        Log.v("sjy", layoutParams.width + "   " + layoutParams.height);
        //异步加载
          new MyAscTask().execute(myHoler);
//        helpSetHeightAndBitmap(myHoler.imageView,i);
    }
    private int calSampleSize(BitmapFactory.Options options, int reqWidth){
        int size=1;
        while (options.outWidth/size>reqWidth)
        {
            size*=2;
        }
        Log.v("sjy",size+"");
        return size;
    }
    @Override
    public int getItemCount() {
        return dishMenuInfos.size();
    }

    public static class MyHoler extends RecyclerView.ViewHolder{
        View itemView;
        ImageView imageView;
        TextView textView;
        Integer position;
        public MyHoler(View itemView,Integer position) {
            super(itemView);
            this.itemView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv_dishes);
            textView= (TextView) itemView.findViewById(R.id.tv_dishes);
            this.position=position;

        }
    }

    public class MyAscTask extends AsyncTask<MyHoler,Object,Bitmap>{
        MyHoler myHoler;


        @Override
        protected Bitmap doInBackground(MyHoler... params) {
            myHoler = params[0];
            //缓存有bitmap直接返回
            if (lruCache.get(myHoler.position) != null) {
              Log.v("sjy","lrucache has");
                return lruCache.get(myHoler.position);
            }//缓存无bitmap要加载
            final BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(context.getResources(),imageLab[myHoler.position%8],options);
            options.inSampleSize=calSampleSize(options,imageWidth);
            options.inJustDecodeBounds=false;
            Log.v("sjy","samplesize is "+options.inSampleSize+"imageWidth is "+imageWidth);
            Log.v("sjy","befor decode height is "+options.outHeight+"width is"+options.outWidth);

            Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), imageLab[myHoler.position%8], options);

            Log.v("sjy","after decode height is "+bitmap.getHeight()+"width is"+bitmap.getWidth());
            lruCache.put(myHoler.position,bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            myHoler.imageView.setImageBitmap(bitmap);
            Log.v("ggg", dishMenuInfos.get(myHoler.position).getDiningRoomName());
            myHoler.textView.setText(dishMenuInfos.get(myHoler.position).getMealName() + "  ￥" + dishMenuInfos.get(myHoler.position).getMealPrice() + "/份");
            myHoler.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickedListener.onClick(dishMenuInfos.get(myHoler.position).getMealName(),dishMenuInfos.get(myHoler.position).getMealPrice());
                }
            });
        }
    }
   private int converDpToPixel(float dp) {
     Resources resources=context.getResources();
        DisplayMetrics metrics=resources.getDisplayMetrics();
       float px=dp * (metrics.densityDpi/160f);
        return (int) px;
    }

}
