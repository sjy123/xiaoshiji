package fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.tencentmap.mapsdk.map.MapView;

import org.apache.http.Header;

import java.util.ArrayList;

import adapter.DiningRoomListAdapter;
import beans.DiningRoomInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDiningRoom.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDiningRoom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDiningRoom extends Fragment implements TencentLocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListView mListView;
    public MapView mMapView;
    public Button mBack;
    public TextView mCount;

    public FragmentTransaction fragmentTransaction;
    public TencentSearch tencentSearch;
    public SearchParam.Region region;
    public SearchParam searchParam;
    public Location location;
    public SearchParam.Nearby nearBy;
    public double latitude;
    public double longitude;

    public ArrayList<DiningRoomInfo> diningRoomInfos;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDiningRoom.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDiningRoom newInstance(String param1, String param2) {
        FragmentDiningRoom fragment = new FragmentDiningRoom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public FragmentDiningRoom() {
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
        View RootView = inflater.inflate(R.layout.fragment_fragment_dining_room,container,false);

        /*
        接受FragmentAll传递过来的经纬度数据
         */
        Bundle bundle = getArguments();
        if (bundle!=null){
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("lng");

            Log.v("nmbe",String.valueOf(latitude+" "+longitude));
        }

        mListView = (ListView)RootView.findViewById(R.id.listview_diningroom);
        final View mHeadView = inflater.inflate(R.layout.dinindroom_list_head,null);
        mCount = (TextView)mHeadView.findViewById(R.id.diningroom_count);
        mMapView = (MapView)mHeadView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mListView.addHeaderView(mHeadView);

        /*
        腾讯地图的周边搜索功能，关键词是"食堂"
        采用圆形区域检索,搜索半径为1000m
         */
        tencentSearch = new TencentSearch(getActivity());
        location = new Location().lat((float)latitude).lng((float) longitude);
        nearBy = new SearchParam.Nearby().point(location);
        nearBy.r((int)1000f);
        searchParam = new SearchParam().keyword("华科食堂").boundary(nearBy);
        tencentSearch.search(searchParam,new HttpResponseListener() {
            @Override
            public void onSuccess(int i, Header[] headers, BaseObject baseObject) {
                if (baseObject!=null){
                    SearchResultObject searchResultObject = (SearchResultObject)baseObject;
                    if (searchResultObject.data!=null){
                        diningRoomInfos = new ArrayList<DiningRoomInfo>();
                        for(SearchResultObject.SearchResultData data : searchResultObject.data){
                            DiningRoomInfo diningRoomInfo = new DiningRoomInfo();
                            diningRoomInfo.setTitle(data.title);
                            diningRoomInfo.setType(data.type);
                            diningRoomInfo.setAddress(data.address);
                            diningRoomInfo.setCategory(data.category);
                            diningRoomInfo.setId(data.id);
                            diningRoomInfo.setLocation(data.location.lat+" "+data.location.lng);
                            diningRoomInfo.setPano("暂时没有消息");
                            diningRoomInfos.add(diningRoomInfo);
                        }
                        mCount.setText("一共发现"+String.valueOf(diningRoomInfos.size())+"个食堂");
                        DiningRoomListAdapter diningRoomListAdapter=new DiningRoomListAdapter(getActivity(),diningRoomInfos);
                        mListView.setAdapter(diningRoomListAdapter);
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.v("doubi",s);
            }
        });

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        mBack = (Button)RootView.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.container,new FragmentAll()).commit();
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

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {

    }

    @Override
    public void onStatusUpdate(String s, int i, String s2) {

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

}
