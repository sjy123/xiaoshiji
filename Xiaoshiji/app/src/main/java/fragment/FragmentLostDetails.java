package fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityLostDetails;

import beans.LostInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentLostDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentLostDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLostDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolBar;
    public static final String TITLE="Lost详情";
    public LostInfo lostInfo;
    public TextView mLostName,mLostPlace,mLostDescription,mLostContact,mLostAttach;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLostDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLostDetails newInstance(String param1, String param2) {
        FragmentLostDetails fragment = new FragmentLostDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentLostDetails() {
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
        View RootView = inflater.inflate(R.layout.fragment_fragment_lost_details,container, false);

        (((ActivityLostDetails)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivityLostDetails)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        lostInfo = new LostInfo();

        Bundle bundle = this.getArguments();

        if (bundle!=null){
            lostInfo.setLostName(bundle.getString("lostname"));
            lostInfo.setLostPlace(bundle.getString("lostplace"));
            lostInfo.setLostDate(bundle.getString("lostdate"));
            lostInfo.setLostAttach(bundle.getString("lostattach"));
            lostInfo.setLostContact(bundle.getString("lostcontact"));
            lostInfo.setLostDescription(bundle.getString("lostdescription"));
        }

        mLostName  = (TextView)RootView.findViewById(R.id.lost_name);
        mLostPlace = (TextView)RootView.findViewById(R.id.lost_place);
        mLostDescription = (TextView)RootView.findViewById(R.id.lost_description);
        mLostContact = (TextView)RootView.findViewById(R.id.contacttype);
        mLostAttach = (TextView)RootView.findViewById(R.id.lost_attach);

        mLostName.setText(lostInfo.getLostName());
        mLostPlace.setText(lostInfo.getLostPlace());
        mLostDescription.setText(lostInfo.getLostDescription());
        mLostAttach.setText(lostInfo.getLostAttach());
        mLostContact.setText(lostInfo.getLostContact());

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
