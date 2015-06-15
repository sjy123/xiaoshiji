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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.db.xiaoshiji.R;
import com.example.db.xiaoshiji.activity.ActivityPersonMenu;
import com.example.db.xiaoshiji.activity.ActivitySignIn;
import com.example.db.xiaoshiji.activity.ActivitySignUp;

import java.util.ArrayList;
import java.util.List;

import fragment.DiningRoomInfoFragment;
import fragment.DiningRoomInfo_fragmentPos0;
import fragment.DiningRoomInfo_fragmentPos1;
import fragment.DishesDetailFragment;
import fragment.DishesListFragment;
import fragment.FragmentAccountInfo;
import fragment.FragmentAll;
import fragment.FragmentBringMeal;
import fragment.FragmentDiningRoom;
import fragment.FragmentFind;
import fragment.FragmentFound;
import fragment.FragmentHelpDetails;
import fragment.FragmentHome;
import fragment.FragmentLost;
import fragment.FragmentLostDetails;
import fragment.FragmentMy;
import fragment.FragmentPutForward;
import fragment.FragmentPutLost;
import fragment.FragmentSignIn;
import fragment.FragmentSignUp;
import utils.AppConstant;
import view.MaterialDrawer.liveo.interfaces.NavigationLiveoListener;
import view.MaterialDrawer.liveo.navigationliveo.NavigationLiveo;


public class MainActivity extends NavigationLiveo implements  NavigationLiveoListener
                                                            , FragmentAll.OnFragmentInteractionListener
                                                            , FragmentFind.OnFragmentInteractionListener
                                                            , FragmentMy.OnFragmentInteractionListener
                                                            , FragmentDiningRoom.OnFragmentInteractionListener
                                                            , DiningRoomInfoFragment.OnFragmentInteractionListener
                                                            , DishesListFragment.OnFragmentInteractionListener
                                                            , FragmentPutForward.OnFragmentInteractionListener
                                                            , FragmentHelpDetails.OnFragmentInteractionListener
                                                            , FragmentSignUp.OnFragmentInteractionListener
                                                            , FragmentAccountInfo.OnFragmentInteractionListener
                                                            , DiningRoomInfo_fragmentPos0.OnFragmentInteractionListener
                                                            , DiningRoomInfo_fragmentPos1.OnFragmentInteractionListener
                                                            , DishesDetailFragment.OnFragmentInteractionListener
                                                            , FragmentSignIn.OnFragmentInteractionListener
                                                            , FragmentBringMeal.OnFragmentInteractionListener
                                                            , FragmentLost.OnFragmentInteractionListener
                                                            , FragmentFound.OnFragmentInteractionListener
                                                            , FragmentPutLost.OnFragmentInteractionListener
                                                            , FragmentLostDetails.OnFragmentInteractionListener{

    public List<String> mListNameItem;

    @Override
    public void onUserInformation() {
        //User information here
        this.mUserName.setText("db");
        this.mUserEmail.setText("3025673709@qq.com");
        this.mUserPhoto.setImageResource(R.drawable.ic_rudsonlive);
//        this.mUserBackground.setImageResource(R.drawable.ic_user_background);
        this.mUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AVUser avUser = AVUser.getCurrentUser();
                if (avUser!=null){
                    startActivity(new Intent(MainActivity.this, ActivitySignIn.class));
                }else {
                    startActivity(new Intent(MainActivity.this, ActivitySignUp.class));
                }
            }
        });
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.uniBoys.Xiaoshiji", Context.MODE_PRIVATE);
        this.mUserName.setText(sharedPreferences.getString("NAME","db"));

    }

    @Override
    public void onInt(Bundle savedInstanceState) {
        //Creation of the list items is here

        // set listener {required}
        this.setNavigationListener(this);
        if (savedInstanceState == null) {
            //First item of the position selected from the list
            this.setDefaultStartPositionNavigation(1);
        }

        // name of the list items
        mListNameItem = new ArrayList<>();
        mListNameItem.add(0, getString(R.string.inbox));
        mListNameItem.add(1, getString(R.string.starred));
        mListNameItem.add(2, getString(R.string.sent_mail));
//        mListNameItem.add(3, getString(R.string.drafts));
        mListNameItem.add(3, getString(R.string.more_markers)); //This item will be a subHeader
        mListNameItem.add(4, getString(R.string.trash));
        mListNameItem.add(5, getString(R.string.spam));

        // icons list items
        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, 0);
        mListIconItem.add(1, R.drawable.ab_page_cam); //Item no icon set 0
        mListIconItem.add(2, R.drawable.ab_attach_video); //Item no icon set 0
//        mListIconItem.add(3, R.drawable.ic_inbox_black_24dp);
        mListIconItem.add(3, 0); //When the item is a subHeader the value of the icon 0
        mListIconItem.add(4, R.drawable.ic_action_send_now_light);
        mListIconItem.add(5, R.drawable.ab_audio);

        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();
//        mListHeaderItem.add(0);
        mListHeaderItem.add(3);

        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        mSparseCounterItem.put(0, 0);
        mSparseCounterItem.put(1, 1);
        mSparseCounterItem.put(2,1);
        mSparseCounterItem.put(5, 0);

        //If not please use the FooterDrawer use the setFooterVisible(boolean visible) method with value false
        this.setFooterInformationDrawer(R.string.settings, R.drawable.btn_settings);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
    }

    @Override
    public void onItemClickNavigation(int position, int layoutContainerId) {

            FragmentManager mFragmentManager = getSupportFragmentManager();

            Fragment mFragment = new FragmentMain().newInstance(mListNameItem.get(position));

            if (mFragment != null){
                mFragmentManager.beginTransaction().replace(layoutContainerId, mFragment).commit();
            }
    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int position, boolean visible) {

        //hide the menu when the navigation is opens
        switch (position) {
            case 0:
                menu.findItem(R.id.menu_add).setVisible(!visible);
                menu.findItem(R.id.menu_search).setVisible(!visible);
                break;

            case 1:
                menu.findItem(R.id.menu_add).setVisible(!visible);
                menu.findItem(R.id.menu_search).setVisible(!visible);
                break;
        }
    }

    @Override
    public void onClickUserPhotoNavigation(View v) {
        //user photo onClick
        Toast.makeText(this, R.string.open_user_profile, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickFooterItemNavigation(View v) {
        //footer onClick
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
