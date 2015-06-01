package fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
<<<<<<< HEAD
=======
import android.util.Log;
>>>>>>> temp
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.db.xiaoshiji.R;
<<<<<<< HEAD
import com.tencent.tencentmap.mapsdk.map.MapView;
=======
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.tencentmap.mapsdk.map.MapView;

import org.apache.http.Header;
>>>>>>> temp

import adapter.DiningRoomListAdapter;

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

    public FragmentTransaction fragmentTransaction;
<<<<<<< HEAD
=======
    public TencentSearch tencentSearch;
    public SearchParam.Region region;
    public SearchParam searchParam;
>>>>>>> temp

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

<<<<<<< HEAD
=======
        /*
        腾讯地图的周边搜索功能，关键词是"食堂"
        采用圆形区域检索,搜索半径为1000m
         */
        tencentSearch = new TencentSearch(getActivity());
        region = new SearchParam.Region().poi("武汉");
        searchParam = new SearchParam().keyword("食堂").boundary(region);
        tencentSearch.search(searchParam,new HttpResponseListener() {
            @Override
            public void onSuccess(int i, Header[] headers, BaseObject baseObject) {
                if (baseObject!=null){
                    SearchResultObject searchResultObject = (SearchResultObject)baseObject;
                    if (searchResultObject.data!=null){
                        String result = "搜索武汉地区的食堂poi\n\n";
                        for(SearchResultObject.SearchResultData data : searchResultObject.data){
                            Log.v("demo", "title:" + data.address);
                            result += data.address+"\n";
                        }
//                        Log.v("hahah",result);
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.v("doubi",s);
            }
        });

>>>>>>> temp
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        mBack = (Button)RootView.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                fragmentTransaction.replace(R.id.container,new FragmentDiningRoom()).commit();
=======
                fragmentTransaction.replace(R.id.container,new FragmentAll()).commit();
>>>>>>> temp
            }
        });

        mListView = (ListView)RootView.findViewById(R.id.listview_diningroom);
        View mHeadView = inflater.inflate(R.layout.dinindroom_list_head,null);
        mMapView = (MapView)mHeadView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mListView.addHeaderView(mHeadView);
        DiningRoomListAdapter diningRoomListAdapter=new DiningRoomListAdapter(getActivity());
        mListView.setAdapter(diningRoomListAdapter);

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
