package fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import view.RippleBackground;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentAll.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentAll#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAll extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ImageView mFoundDevice;
    public RippleBackground mRippleBackground;
    public TextView mTextViewTip;

    public TencentLocationRequest tencentLocationRequest;
    public TencentLocationManager tencentLocationManager;
    public TencentLocationListener tencentLocationListener;

    public FragmentTransaction fragmentTransaction;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAll.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAll newInstance(String param1, String param2) {
        FragmentAll fragment = new FragmentAll();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentAll() {
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

        View RootView = inflater.inflate(R.layout.fragment_fragment_all,container,false);

        /*
        腾讯定位功能进行定位
        appkey：6QLBZ-TTKAD-M4M4M-P5GUB-WWULF-OWF2Y
        设置成自动缓存模式
         */
        tencentLocationManager = TencentLocationManager.getInstance(getActivity());
        tencentLocationRequest = TencentLocationRequest.create();
        tencentLocationRequest.setAllowCache(true);
        tencentLocationRequest.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_GEO);
        tencentLocationListener = new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                if (i==TencentLocation.ERROR_OK){
                    double lat = tencentLocation.getLatitude();
                    double lng = tencentLocation.getLongitude();
                    Log.v("test",String.valueOf(lat+","+lng+","+String.valueOf(i)));
                }else {
                    Log.v("merror",s);
                }
            }

            @Override
            public void onStatusUpdate(String s, int i, String s2) {

            }
        };


        mFoundDevice = (ImageView)RootView.findViewById(R.id.founddevice);
        mRippleBackground=(RippleBackground)RootView.findViewById(R.id.content);
        mTextViewTip = (TextView)RootView.findViewById(R.id.textview_tip);

        final Handler handler=new Handler();
        mFoundDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRippleBackground.startRippleAnimation();
                mTextViewTip.setText("正在搜索附近的食堂...");
                /*
                创建定位事件，并将地理卫视的数据数据传给FragmentDiningRoom
                */
                int error = tencentLocationManager.requestLocationUpdates(tencentLocationRequest,tencentLocationListener);
                Log.v("mb",String.valueOf(error));


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        foundDevice();
                    }
                },3000);
            }
        });


        return RootView;
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


    private void foundDevice(){

        mRippleBackground.stopRippleAnimation();
        mTextViewTip.setText("点击发现附近的食堂");

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
        fragmentTransaction.replace(R.id.container,new FragmentDiningRoom()).commit();

    }
}
