package fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;

import adapter.DishCommentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DishesDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DishesDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DishesDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public static final String TITLE="菜品详情";
    public static final String SUBTITLE="东一食堂";
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DishesDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DishesDetailFragment newInstance(String param1, String param2) {
        DishesDetailFragment fragment = new DishesDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DishesDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dishes_detail, container, false);

        (((MainActivity)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Toolbar toolBar=(((MainActivity)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        toolBar.setSubtitle(SUBTITLE);
        ListView listView= (ListView) view.findViewById(R.id.lv_dish);
        View headerView=inflater.inflate(R.layout.dish_list_header,null);
        //装载图片
        ImageView dish_image= (ImageView) headerView.findViewById(R.id.iv_dish);
        int width=getActivity().getWindowManager().getDefaultDisplay().getWidth();
        helpSetHeightAndBitmap(dish_image, width);

        listView.addHeaderView(headerView);
        listView.setAdapter(new DishCommentAdapter());
        headerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    public void helpSetHeightAndBitmap(ImageView imageView,int imageWidth){


            ViewGroup.LayoutParams layoutParams =imageView.getLayoutParams();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.profile2, options);

            int height= (int) ((imageWidth*1.0*options.outHeight)/options.outWidth);
            options.inJustDecodeBounds=false;
            options.inSampleSize=options.outWidth/imageWidth;
            layoutParams.height=height;
            layoutParams.width=imageWidth;
            imageView.setLayoutParams(layoutParams);
            Bitmap bitmap= BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.profile2, options);
            imageView.setImageBitmap(bitmap);


    }
}
