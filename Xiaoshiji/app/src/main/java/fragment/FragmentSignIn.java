package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.db.xiaoshiji.MainActivity;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityFoundDetails;
import com.example.db.xiaoshiji.activity.ActivitySignIn;
import com.example.db.xiaoshiji.activity.ActivitySignUp;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSignIn.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSignIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSignIn extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Toolbar toolBar;
    public static final String TITLE="登陆";

    public Button mSignIn;
    public EditText mUserName,mUserPsd;
    public String username,userpsd;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSignIn.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSignIn newInstance(String param1, String param2) {
        FragmentSignIn fragment = new FragmentSignIn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentSignIn() {
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
        View RootView = inflater.inflate(R.layout.fragment_fragment_sign_in, container, false);

        (((ActivitySignIn)getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolBar=(((ActivitySignIn)getActivity()).getToolbar());
        toolBar.setTitle(TITLE);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mUserName = (EditText)RootView.findViewById(R.id.user_name);
        mUserPsd = (EditText)RootView.findViewById(R.id.user_psd);
        mSignIn = (Button)RootView.findViewById(R.id.btn_signin);

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUserName.getText().toString();
                userpsd = mUserPsd.getText().toString();
                if (userpsd.length()!=0&&username.length()!=0){
                    AVUser.logInInBackground(username, userpsd, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e==null){
                                /*
                                存储本地账户的相关信息
                                包括姓名、登陆状态
                                在初始的时候显示
                                 */
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.uniBoys.Xiaoshiji", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("NAME",avUser.getUsername());
                                editor.putBoolean("STATUS", true);
                                editor.commit();
                                getActivity().getSupportFragmentManager()
                                             .beginTransaction()
                                             .replace(R.id.container,new FragmentMy())
                                             .commit();
                                Toast.makeText(getActivity(),"登陆成功惹~",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"登陆失败惹~",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
