package com.example.farouk.roomx.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.booking.rtlviewpager.RtlViewPager;
import com.example.farouk.roomx.R;
import com.example.farouk.roomx.ui.chat.InboxFragment;
import com.example.farouk.roomx.ui.explore.ExploreFragment;
import com.example.farouk.roomx.ui.favourit.FavouritFragment;
import com.example.farouk.roomx.ui.account.AccountFragment;
import com.example.farouk.roomx.ui.reservation.ReservationsFragment;
import com.example.farouk.roomx.util.Const;
import com.example.farouk.roomx.util.FragmentType;
import com.example.farouk.roomx.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class IconTextTabsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private TabLayout tabLayout;
    private RtlViewPager viewPager;
    boolean isbehost = false;
    private int[] tabIcons = {
            R.drawable.ic_search
            ,R.drawable.ic_reserv
            ,R.drawable.ic_fav
            ,R.drawable.ic_chat
            ,R.drawable.ic_account
    };

    private int[] tabIcons_behost = {
            R.drawable.ic_search
            ,R.drawable.ic_fav
            ,R.drawable.ic_chat
            ,R.drawable.ic_account
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeLang(getBaseContext(),"ar");
        setContentView(R.layout.activity_icon_text_tabs);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             isbehost = extras.getBoolean(Const.BE_HOST);
            if (isbehost) {
                // Do something
                isbehost=true;
                Log.d("isbehost","true");
            } else {
                // Do something else
                isbehost=false;
            }
        }
        viewPager = (RtlViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager,isbehost);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(isbehost);

    }

    private void setupTabIcons(boolean isbehost) {
        if(isbehost){
            tabLayout.getTabAt(0).setIcon(tabIcons_behost[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons_behost[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons_behost[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons_behost[3]);

        }else {
            tabLayout.getTabAt(0).setIcon(tabIcons[0]);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
            tabLayout.getTabAt(3).setIcon(tabIcons[3]);
            tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        }
    }

    private void setupViewPager(ViewPager viewPager, boolean isbehost) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(isbehost) {
            adapter.addFrag(ExploreFragment.newInstance(FragmentType.MY_ROOMS.getValue()), getResources().getString(R.string.title_activity_my_room));
            adapter.addFrag(ReservationsFragment.newInstance(FragmentType.RESERVATION_REQUESTS.getValue()), getResources().getString(R.string.title_activity_reservation_requests));
            adapter.addFrag(new InboxFragment(), getResources().getString(R.string.title_activity_add_room));
            adapter.addFrag(new AccountFragment(), getResources().getString(R.string.title_activity_account));
        }else{
            adapter.addFrag(new ExploreFragment(), getResources().getString(R.string.title_activity_explore));
            adapter.addFrag(new ReservationsFragment(), getResources().getString(R.string.title_activity_reserve));
            adapter.addFrag(new FavouritFragment(), getResources().getString(R.string.title_activity_fav));
            adapter.addFrag(new InboxFragment(), getResources().getString(R.string.title_activity_inbox));
            adapter.addFrag(new AccountFragment(), getResources().getString(R.string.title_activity_account));
        }

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
