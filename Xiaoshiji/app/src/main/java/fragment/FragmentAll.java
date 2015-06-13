package fragment;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.tencent.map.geolocation.TencentLocation;

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

    private Toolbar toolBar;
    public static final String TITLE="附近";

    public ImageView mFoundDevice;
    public RippleBackground mRippleBackground;
    public TextView mTextViewTip;

    public LocationManager locationManager;
    public Location location;
    public double latitude;
    public double longitude;

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
        (((MainActivity)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toolBar=(((MainActivity)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setSubtitle(null);
        /*
        利用系统自带的GPS和NetWork来实现定位
         */
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }else {
            LocationListener locationListener = new LocationListener() {

                // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                // Provider被enable时触发此函数，比如GPS被打开
                @Override
                public void onProviderEnabled(String provider) {

                }

                // Provider被disable时触发此函数，比如GPS被关闭
                @Override
                public void onProviderDisabled(String provider) {

                }

                //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        Log.e("Map", "Location changed : Lat: "
                                + location.getLatitude() + " Lng: "
                                + location.getLongitude());
                    }
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location != null)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }


        mFoundDevice = (ImageView)RootView.findViewById(R.id.founddevice);
        mRippleBackground=(RippleBackground)RootView.findViewById(R.id.content);
        mTextViewTip = (TextView)RootView.findViewById(R.id.textview_tip);

        final Handler handler=new Handler();
        mFoundDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRippleBackground.startRippleAnimation();
                mTextViewTip.setText("正在搜索附近的食堂...");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        foundDevice();
                    }
                }, 3000);
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

        /*
        创建定位事件，并将地理卫视的数据数据传给FragmentDiningRoom
        */
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat",latitude);
        bundle.putDouble("lng",longitude);
        FragmentDiningRoom fragmentDiningRoom = new FragmentDiningRoom();
        fragmentDiningRoom.setArguments(bundle);
        fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
        fragmentTransaction.replace(R.id.container,fragmentDiningRoom).addToBackStack(null).commit();

        Log.v("nmba",String.valueOf(latitude+" "+longitude));

    }
}
