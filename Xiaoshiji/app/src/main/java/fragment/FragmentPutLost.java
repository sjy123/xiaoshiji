package fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityLost;
import com.example.db.xiaoshiji.activity.ActivityPutLost;

import beans.LostInfo;
import utils.AppConstant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPutLost.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPutLost#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPutLost extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolBar;
    public static final String TITLE="发布失物";

    public EditText mLostName,mLostPlace,mLostDescription,mLostContact,mLostAttach;
    public Button mSend;
    public String lostname,lostplace,lostdescription,lostcontact,lostattach;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPutLost.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPutLost newInstance(String param1, String param2) {
        FragmentPutLost fragment = new FragmentPutLost();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentPutLost() {
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
        View RootView = inflater.inflate(R.layout.fragment_fragment_put_lost, container, false);

        (((ActivityPutLost)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivityPutLost)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mLostName  = (EditText)RootView.findViewById(R.id.lost_name);
        mLostPlace = (EditText)RootView.findViewById(R.id.lost_place);
        mLostDescription = (EditText)RootView.findViewById(R.id.lost_description);
        mLostContact = (EditText)RootView.findViewById(R.id.contacttype);
        mLostAttach = (EditText)RootView.findViewById(R.id.lost_attach);
        mSend = (Button)RootView.findViewById(R.id.putforward);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lostname = mLostName.getText().toString();
                lostplace = mLostPlace.getText().toString();
                lostdescription = mLostDescription.getText().toString();
                lostattach = mLostAttach.getText().toString();
                lostcontact = mLostContact.getText().toString();

                if (lostname!=null&&lostplace!=null&&lostdescription!=null&&lostattach!=null&&lostcontact!=null){
                    /*
                    验证手机号
                     */
                    if (AppConstant.isMobile(lostcontact)){

                        LostInfo lostInfo = new LostInfo();
                        lostInfo.setLostName(lostname);
                        lostInfo.setLostPlace(lostplace);
                        lostInfo.setLostDescription(lostdescription);
                        lostInfo.setLostAttach(lostattach);
                        lostInfo.setLostContact(lostcontact);
                        lostInfo.setLostDate(AppConstant.getCurrentTime());

                        lostInfo.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e==null){
                                    Toast.makeText(getActivity(),"发布成功惹~",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), ActivityLost.class));
                                    Log.v("mlgb", "successful");
                                }else {
                                    Toast.makeText(getActivity(),"发布失败惹,查看下网络吧~",Toast.LENGTH_SHORT).show();
                                    Log.v("mmmlegb",e.getMessage());
                                }
                            }
                        });

                    }else {
                        Toast.makeText(getActivity(),"请输入正确的手机号码!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "请输入完整的信息!", Toast.LENGTH_SHORT).show();
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
