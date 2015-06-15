/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package drawer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.xiaoshiji.R;

import fragment.FragmentAll;
import fragment.FragmentFind;
import fragment.FragmentMy;


public class FragmentMain extends Fragment {

    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";

	private TabLayout tabLayout;

	public FragmentMain newInstance(String text){
		FragmentMain mFragment = new FragmentMain();
		Bundle mBundle = new Bundle();
		mBundle.putString(TEXT_FRAGMENT, text);
		mFragment.setArguments(mBundle);
		return mFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.fragment_fragment_home, container, false);

		//设置tabLayout
		tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout);

		android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(R.id.container_home, new FragmentAll());
		fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
		fragmentTransaction.commit();

		tabLayout.addTab(tabLayout.newTab().setText("附近"));
		tabLayout.addTab(tabLayout.newTab().setText("发现"));
		tabLayout.addTab(tabLayout.newTab().setText("我的"));

		tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				Log.v("sjy", "selected" + tab.getPosition());
				android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
				if (tab.getPosition()==0)
				{
					fragmentTransaction.replace(R.id.container_home, new FragmentAll());
					fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
					fragmentTransaction.commit();
				}else if (tab.getPosition()==1){
					fragmentTransaction.replace(R.id.container_home,new FragmentFind());
					fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
					fragmentTransaction.commit();
				}else {
					fragmentTransaction.replace(R.id.container_home,new FragmentMy());
					fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
					fragmentTransaction.commit();
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				Log.v("sjy","unselected"+tab.getPosition());

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				Log.v("sjy","reselected"+tab.getPosition());

			}
		});

		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu, menu);
        
        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

		menu.findItem(R.id.menu_add).setVisible(true);

		mSearchCheck = false;	
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch (item.getItemId()) {

		case R.id.menu_add:
            Toast.makeText(getActivity(), R.string.add, Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu_search:
			mSearchCheck = true;
            Toast.makeText(getActivity(), R.string.search, Toast.LENGTH_SHORT).show();
			break;
		}
		return true;
	}	

   private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String s) {
           return false;
       }

       @Override
       public boolean onQueryTextChange(String s) {
           if (mSearchCheck){
               // implement your search here
           }
           return false;
       }
   };
}
