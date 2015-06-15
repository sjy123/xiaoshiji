package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivitySignIn;
import com.example.db.xiaoshiji.activity.ActivitySignUp;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentMy.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentMy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMy extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolBar;
    public static final String TITLE="我的";
    public CircleImageView mLogo;

    public TextView mPersonalMenu,mPutForward,mSetting,mPersonName;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMy.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMy newInstance(String param1, String param2) {
        FragmentMy fragment = new FragmentMy();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMy() {
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

        View RootView = inflater.inflate(R.layout.fragment_fragment_my, container, false);

//        (((MainActivity)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
//        toolBar=(((MainActivity)getActivity()).getToolbar());
//        toolBar.setTitle(TITLE);
//        toolBar.setSubtitle(null);

        mPersonalMenu = (TextView)RootView.findViewById(R.id.person_menu);
        mPutForward = (TextView)RootView.findViewById(R.id.person_notice);
        mSetting = (TextView)RootView.findViewById(R.id.person_settings);
        mPersonName = (TextView)RootView.findViewById(R.id.person_name);

        mLogo = (CircleImageView)RootView.findViewById(R.id.person_logo);
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser avUser = AVUser.getCurrentUser();
                if (avUser!=null){
//                    getActivity().getSupportFragmentManager()
//                                 .beginTransaction()
//                                 .replace(R.id.container, new FragmentSignIn())
//                                 .addToBackStack(null)
//                                 .commit();
                    startActivity(new Intent(getActivity(), ActivitySignIn.class));
                }else {
//                    getActivity().getSupportFragmentManager()
//                                 .beginTransaction()
//                                 .replace(R.id.container, new FragmentSignUp())
//                                 .addToBackStack(null)
//                                 .commit();
                    startActivity(new Intent(getActivity(), ActivitySignUp.class));
                }
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.uniBoys.Xiaoshiji", Context.MODE_PRIVATE);
        mPersonName.setText(sharedPreferences.getString("NAME","db"));

        mPersonalMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mPutForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
