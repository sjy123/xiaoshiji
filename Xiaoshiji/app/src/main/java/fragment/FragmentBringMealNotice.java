package fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityHelpDetails;

import java.util.ArrayList;

import adapter.FindListAdapter;
import beans.BringMealInfo;
import utils.LeanCloudService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBringMealNotice.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBringMealNotice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBringMealNotice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SwipeRefreshLayout mSwipeRefreshLayout;

    public ListView mListView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBringMealInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBringMealNotice newInstance(String param1, String param2) {
        FragmentBringMealNotice fragment = new FragmentBringMealNotice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentBringMealNotice() {
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
        View RootView = inflater.inflate(R.layout.fragment_fragment_bring_meal_notice, container, false);

        mListView = (ListView)RootView.findViewById(R.id.listview_find);
        View mHeadView = inflater.inflate(R.layout.common_head,null);
        mListView.addHeaderView(mHeadView);
        mSwipeRefreshLayout=(SwipeRefreshLayout)RootView.findViewById(R.id.find_refreshlayout);

        new RemoteDataTask().execute();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RemoteDataTask().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
    public class RemoteDataTask extends AsyncTask<Void, Void, ArrayList<BringMealInfo>> {

        public AVQuery<AVObject> query;
        public ArrayList<BringMealInfo> bringMealInfos = new ArrayList<BringMealInfo>();

        @Override
        protected ArrayList<BringMealInfo> doInBackground(Void... params) {

            bringMealInfos = LeanCloudService.findBringMealInfos();

            return bringMealInfos;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(final ArrayList<BringMealInfo> result) {

            if (result!=null){
                final FindListAdapter findListAdapter=new FindListAdapter(getActivity(),result);
                mListView.setAdapter(findListAdapter);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                        FragmentHelpDetails fragmentHelpDetails = new FragmentHelpDetails();
                        Intent intent =new Intent(getActivity(),ActivityHelpDetails.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("mealname",result.get(result.size()-i).getMealname());
                        bundle.putString("mealtype",result.get(result.size()-i).getMealtype());
                        bundle.putString("paytype",result.get(result.size()-i).getPaytype());
                        bundle.putString("contacttype",result.get(result.size()-i).getContacttype());
                        bundle.putString("destination",result.get(result.size()-i).getDestination());
                        intent.putExtras(bundle);
                        startActivity(intent);
//                        fragmentHelpDetails.setArguments(bundle);
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentHelpDetails).addToBackStack(null).commit();

                    }
                });
            }else {
                Toast.makeText(getActivity(), "没有发现惹~", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
