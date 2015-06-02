package fragment;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.example.db.xiaoshiji.R;

import adapter.DiningRoomCommentListAdpter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiningRoomInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiningRoomInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiningRoomInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String diningRoomName;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView listView;
    private DiningRoomCommentListAdpter diningRoomCommentListAdpter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiningRoomInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiningRoomInfoFragment newInstance(String param1, String param2) {
        DiningRoomInfoFragment fragment = new DiningRoomInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DiningRoomInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            diningRoomName = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dining_room_info, container, false);
        TabHost tabHost= (TabHost) view.findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("1").setIndicator("食堂信息",
                getResources().getDrawable(R.drawable.ic_launcher)).setContent(
                R.id.view1));

        tabHost.addTab(tabHost.newTabSpec("2").setIndicator("食堂评价",
                getResources().getDrawable(R.drawable.ic_launcher)).setContent(
                R.id.view2));
        TabWidget tabWidget=tabHost.getTabWidget();
        for (int i=0;i<tabWidget.getChildCount();i++)
        {
            View v=tabWidget.getChildAt(i);
            v.setBackgroundResource(R.drawable.tab_indicator_ab_example);
        }
        diningRoomCommentListAdpter = new DiningRoomCommentListAdpter();
        listView = (ListView) tabHost.findViewById(R.id.listview_comment);
        listView.setAdapter(diningRoomCommentListAdpter);
//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                if (Integer.parseInt(tabId)==2)
//                {
//
//                }
//            }
//        });
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

}
