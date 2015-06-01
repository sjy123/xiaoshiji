package fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.db.xiaoshiji.R;
import com.tencent.tencentmap.mapsdk.map.MapView;

import adapter.DiningRoomListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentDiningRoom.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentDiningRoom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDiningRoom extends Fragment {
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

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        mBack = (Button)RootView.findViewById(R.id.back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.container,new FragmentDiningRoom()).commit();
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