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
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityFoundDetails;
import com.example.db.xiaoshiji.activity.ActivitySignIn;
import com.example.db.xiaoshiji.activity.ActivitySignUp;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSignUp.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignUp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Toolbar toolBar;
    public static final String TITLE="注册";

    public Button mSignUp;
    public TextView mSendCheckCode;
    public EditText mUserName,mUserPsd,mCheckCode;
    public String username,userpsd,usercheckcode;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSignUp newInstance(String param1, String param2) {
        FragmentSignUp fragment = new FragmentSignUp();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentSignUp() {
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

        View RootView = inflater.inflate(R.layout.fragment_fragment_sign_up, container, false);

        (((ActivitySignUp)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivitySignIn)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mSignUp = (Button)RootView.findViewById(R.id.btn_signup);
        mSendCheckCode = (TextView)RootView.findViewById(R.id.acquire_key);

        mUserName = (EditText)RootView.findViewById(R.id.user_name);
        mUserPsd = (EditText)RootView.findViewById(R.id.user_psd);
        mCheckCode = (EditText)RootView.findViewById(R.id.user_check);

        mSendCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUserName.getText().toString();
                userpsd = mUserPsd.getText().toString();
                if (username.length()!=0&&userpsd.length()!=0){
                    AVUser avUser = new AVUser();
                    avUser.setUsername(username);
                    avUser.setPassword(userpsd);
                    avUser.setMobilePhoneNumber(username);
                    Toast.makeText(getActivity(),"正在发送验证码...",Toast.LENGTH_SHORT).show();
                    avUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                mSendCheckCode.setText("60s后重发");
                            } else {
                                Log.v("leanclouderror", e.getMessage());
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"用户名和密码不能为空惹~",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUserName.getText().toString();
                userpsd = mUserPsd.getText().toString();
                if (username!=null&&userpsd!=null){
                    AVUser avUser = new AVUser();
                    avUser.setUsername(username);
                    avUser.setPassword(userpsd);
                    avUser.setMobilePhoneNumber(username);
                    usercheckcode = mCheckCode.getText().toString();
                    if (usercheckcode!=null){
                        avUser.verifyMobilePhoneInBackground(usercheckcode, new AVMobilePhoneVerifyCallback() {
                            @Override
                            public void done(AVException e) {
                                // TODO Auto-generated method stub
                                if (e==null){
                                    Toast.makeText(getActivity(),"注册成功惹~",Toast.LENGTH_SHORT).show();
//                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new FragmentSignIn()).commit();
                                startActivity(new Intent(getActivity(),ActivitySignUp.class));
                                }else {
                                    Toast.makeText(getActivity(),"注册失败惹~",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }else {
                        Toast.makeText(getActivity(),"请输入验证码惹~",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(),"用户名和密码不能为空惹~",Toast.LENGTH_SHORT).show();
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
