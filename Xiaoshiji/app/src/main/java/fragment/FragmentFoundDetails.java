package fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityFoundDetails;
import com.example.db.xiaoshiji.activity.ActivityLostDetails;

import beans.FoundInfo;
import beans.LostInfo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFoundDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFoundDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFoundDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolBar;
    public static final String TITLE="Found详情";
    public FoundInfo foundInfo;
    public TextView mFoundName,mFoundPlace,mFoundDescription,mFoundContact,mFoundAttach;
    public Button mHelp;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FramentFoundDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFoundDetails newInstance(String param1, String param2) {
        FragmentFoundDetails fragment = new FragmentFoundDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentFoundDetails() {
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
        View RootView = inflater.inflate(R.layout.fragment_frament_found_details,container, false);

        (((ActivityFoundDetails)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivityFoundDetails)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        foundInfo = new FoundInfo();

        Bundle bundle = this.getArguments();

        if (bundle!=null){
            foundInfo.setFoundName(bundle.getString("foundname"));
            foundInfo.setFoundPlace(bundle.getString("foundplace"));
            foundInfo.setFoundDate(bundle.getString("founddate"));
            foundInfo.setFoundAttach(bundle.getString("foundattach"));
            foundInfo.setFoundContact(bundle.getString("foundcontact"));
            foundInfo.setFoundDescription(bundle.getString("founddescription"));
        }

        mFoundName  = (TextView)RootView.findViewById(R.id.found_name);
        mFoundPlace = (TextView)RootView.findViewById(R.id.found_place);
        mFoundDescription = (TextView)RootView.findViewById(R.id.found_description);
        mFoundContact = (TextView)RootView.findViewById(R.id.contacttype);
        mFoundAttach = (TextView)RootView.findViewById(R.id.found_attach);

        mHelp = (Button)RootView.findViewById(R.id.putforward);

        mFoundName.setText(foundInfo.getFoundName());
        mFoundPlace.setText(foundInfo.getFoundPlace());
        mFoundDescription.setText(foundInfo.getFoundDescription());
        mFoundAttach.setText(foundInfo.getFoundAttach());
        mFoundContact.setText(foundInfo.getFoundContact());

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                调用系统自带的拨打电话功能
                 */
                if (foundInfo.getFoundContact()!=null){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:"+foundInfo.getFoundContact()));
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "输入的手机号码不能为空惹~", Toast.LENGTH_SHORT).show();
                }
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

}
