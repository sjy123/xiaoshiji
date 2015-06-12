package fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityHelpDetails;

import beans.BringMealInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHelpDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHelpDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHelpDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolBar;
    public static final String TITLE="帮助详情";
    public TextView mealname,mealtype,contacttype,destination,paytype;
    public BringMealInfo bringMealInfo;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHelpDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHelpDetails newInstance(String param1, String param2) {
        FragmentHelpDetails fragment = new FragmentHelpDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentHelpDetails() {
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

        View RootView = inflater.inflate(R.layout.fragment_fragment_help_details, container, false);
        (((ActivityHelpDetails)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivityHelpDetails)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        bringMealInfo = new BringMealInfo();

        Bundle bundle = this.getArguments();
        if ((bundle!=null)){
            bringMealInfo.setMealname(bundle.getString("mealname"));
            bringMealInfo.setMealtype(bundle.getString("mealtype"));
            bringMealInfo.setDestination(bundle.getString("destination"));
            bringMealInfo.setContacttype(bundle.getString("contacttype"));
            bringMealInfo.setPaytype(bundle.getString("paytype"));
        }

        mealname = (TextView)RootView.findViewById(R.id.mealname);
        mealtype = (TextView)RootView.findViewById(R.id.mealtype);
        destination = (TextView)RootView.findViewById(R.id.destination);
        paytype = (TextView)RootView.findViewById(R.id.paytype);
        contacttype = (TextView)RootView.findViewById(R.id.contacttype);

        mealname.setText(bringMealInfo.getMealname());
        mealtype.setText(bringMealInfo.getMealtype());
        destination.setText(bringMealInfo.getDestination());
        paytype.setText(bringMealInfo.getPaytype());
        contacttype.setText(bringMealInfo.getContacttype());

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
