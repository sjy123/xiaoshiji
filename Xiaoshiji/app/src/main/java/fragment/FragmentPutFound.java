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
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityFound;
import com.example.db.xiaoshiji.activity.ActivityLost;
import com.example.db.xiaoshiji.activity.ActivityPutFound;
import com.example.db.xiaoshiji.activity.ActivityPutLost;

import beans.FoundInfo;
import utils.AppConstant;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPutFound.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPutFound#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPutFound extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Toolbar toolBar;
    public static final String TITLE="发布Found";
    public EditText mFoundName,mFoundPlace,mFoundDescription,mFoundContact,mFoundAttach;
    public Button mSend;
    public String foundname,foundplace,founddescription,foundattach,foundcontact;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPutFound.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPutFound newInstance(String param1, String param2) {
        FragmentPutFound fragment = new FragmentPutFound();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentPutFound() {
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
        View RootView = inflater.inflate(R.layout.fragment_fragment_put_found, container, false);

        (((ActivityPutFound)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivityPutFound)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mFoundName  = (EditText)RootView.findViewById(R.id.found_name);
        mFoundPlace = (EditText)RootView.findViewById(R.id.found_place);
        mFoundDescription = (EditText)RootView.findViewById(R.id.found_description);
        mFoundContact = (EditText)RootView.findViewById(R.id.contacttype);
        mFoundAttach = (EditText)RootView.findViewById(R.id.found_attach);
        mSend = (Button)RootView.findViewById(R.id.putforward);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                foundname = mFoundName.getText().toString();
                foundplace = mFoundPlace.getText().toString();
                founddescription = mFoundDescription.getText().toString();
                foundattach = mFoundAttach.getText().toString();
                foundcontact = mFoundContact.getText().toString();

                if (foundname!=null&&foundplace!=null&&founddescription!=null&&foundattach!=null&&mFoundContact!=null){
                    /*
                    验证手机号
                     */
                    if (AppConstant.isMobile(foundcontact)){

                        FoundInfo foundInfo = new FoundInfo();
                        foundInfo.setFoundName(foundname);
                        foundInfo.setFoundPlace(foundplace);
                        foundInfo.setFoundAttach(foundattach);
                        foundInfo.setFoundContact(foundcontact);
                        foundInfo.setFoundDescription(founddescription);
                        foundInfo.setFoundDate(AppConstant.getCurrentTime());

                        foundInfo.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e==null){
                                    Toast.makeText(getActivity(),"发布成功惹~",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), ActivityFound.class));
                                    Log.v("mlgb", "successful");
                                }else {
                                    Toast.makeText(getActivity(),"发布失败惹,查看下网络吧~",Toast.LENGTH_SHORT).show();
                                    Log.v("mmmlegb",e.getMessage());
                                }
                            }
                        });

                    }else {
                        Toast.makeText(getActivity(), "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
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
