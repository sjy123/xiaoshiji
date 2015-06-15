package fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.DiningRoomInfoActivity;
import com.example.db.xiaoshiji.activity.ActivityDiningRoomInfo;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.result.SearchResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.OverlayItem;

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

    private Toolbar toolBar;
    public static final String TITLE="附近的食堂";

    private boolean mSearchCheck;

    public ListView mListView;
    public MapView mMapView;
    public TextView mCount;

    public FragmentTransaction fragmentTransaction;
    public TencentSearch tencentSearch;
    public SearchParam.Region region;
    public SearchParam searchParam;
    public Location location;
    public SearchParam.Nearby nearBy;
    public double latitude;
    public double longitude;
    public ArrayList<GeoPoint> geoPoints;
    public ArrayList<OverlayItem> overlayItems;
    public Drawable marker;
    public ArrayList<LatLng> latLngs;
    public int iTipTranslateX = 0;
    public int iTipTranslateY = 0;


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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.fragment_fragment_dining_room,container,false);
        (((ActivityDiningRoomInfo)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivityDiningRoomInfo)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
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
        marker = getResources().getDrawable(R.drawable.ic_event_location);
        this.iTipTranslateY = marker.getIntrinsicHeight();
        marker.setBounds(0, 0, marker.getIntrinsicWidth(),
                marker.getIntrinsicHeight());
        nearBy.r((int)1000f);
        searchParam = new SearchParam().keyword("华科食堂").boundary(nearBy);
        tencentSearch.search(searchParam,new HttpResponseListener() {
            @Override
            public void onSuccess(int i, Header[] headers, BaseObject baseObject) {
                if (baseObject!=null){
                    SearchResultObject searchResultObject = (SearchResultObject)baseObject;
                    if (searchResultObject.data!=null){
                        diningRoomInfos = new ArrayList<DiningRoomInfo>();
                        latLngs = new ArrayList<LatLng>();
                        geoPoints = new ArrayList<GeoPoint>();
                        overlayItems = new ArrayList<OverlayItem>();
                        for(SearchResultObject.SearchResultData data : searchResultObject.data){
                            DiningRoomInfo diningRoomInfo = new DiningRoomInfo();
                            diningRoomInfo.setTitle(data.title);
                            diningRoomInfo.setType(data.type);
                            diningRoomInfo.setAddress(data.address);
                            diningRoomInfo.setCategory(data.category);
                            diningRoomInfo.setId(data.id);
                            diningRoomInfo.setLocation(data.location.lat + " " + data.location.lng);
                            diningRoomInfo.setPano("暂时没有消息");
                            GeoPoint geoPoint = new GeoPoint((int)(data.location.lat*1E6),(int)(data.location.lng*1E6));
                            OverlayItem overlayItem = new OverlayItem(geoPoint,data.title,"",marker);
                            LatLng latLng = new LatLng(data.location.lat,data.location.lng);
                            geoPoints.add(geoPoint);
                            overlayItems.add(overlayItem);
                            latLngs.add(latLng);
                            diningRoomInfos.add(diningRoomInfo);
                        }
                        mCount.setText("一共发现"+String.valueOf(diningRoomInfos.size())+"个食堂");
                        if (diningRoomInfos!=null){
                            DiningRoomListAdapter diningRoomListAdapter=new DiningRoomListAdapter(getActivity(),diningRoomInfos);
                            mListView.setAdapter(diningRoomListAdapter);
                        }else {
                            Toast.makeText(getActivity(),"没有发现食堂惹~",Toast.LENGTH_SHORT).show();
                        }

                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.replace(R.id.container,DiningRoomInfoFragment.newInstance("名字","地点"),"");
//                                fragmentTransaction.addToBackStack(null);
//                                fragmentTransaction.commit();
                                Intent intent=new Intent(getActivity(), DiningRoomInfoActivity.class);
                                startActivity(intent);


                            }
                        });

                        /*
                        利用腾讯地图的标注功能对附近的食堂进行标注
                        */
                        for (int j=0;j<overlayItems.size()-1;j++){
                            mMapView.add(overlayItems.get(j));
                            mMapView.addMarker(new MarkerOptions()
                                    .position(new LatLng(latLngs.get(j).getLatitude(),latLngs.get(j).getLongitude()))
                                    .title("武汉")
                                    .anchor(0.5f, 0.5f)
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker())
                                    .draggable(true));
                        }

                        mMapView.refreshMap();
                        mMapView.invalidate();

                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Log.v("doubi",s);
            }
        });


        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();


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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

        menu.findItem(R.id.menu_add).setVisible(true);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case R.id.menu_add:
                Toast.makeText(getActivity(), R.string.add, Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_search:
                mSearchCheck = true;
                Toast.makeText(getActivity(), R.string.search, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };
}
