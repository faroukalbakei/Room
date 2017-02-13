package com.example.farouk.roomx.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.booking.rtlviewpager.RtlViewPager;
import com.example.farouk.roomx.R;
import com.example.farouk.roomx.ui.chat.InboxFragment;
import com.example.farouk.roomx.ui.explore.ExploreFragment;
import com.example.farouk.roomx.ui.favourit.FavouritFragment;
import com.example.farouk.roomx.ui.profile.AccountFragment;
import com.example.farouk.roomx.ui.reservation.ReservationsFragment;
import com.example.farouk.roomx.util.NetworkConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;


public class IconTextTabsActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private TabLayout tabLayout;
    private RtlViewPager viewPager;
    private int[] tabIcons = {
            android.R.drawable.ic_menu_search
            ,R.drawable.like
            ,R.drawable.useer
            ,android.R.drawable.sym_action_chat
            ,R.drawable.ontacts
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkConnection.changeLang(this,"ar");
        setContentView(R.layout.activity_icon_text_tabs);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        viewPager = (RtlViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ExploreFragment(), getResources().getString(R.string.title_activity_explore));
        adapter.addFrag(new FavouritFragment(), getResources().getString(R.string.title_activity_fav));
        adapter.addFrag(new ReservationsFragment(), getResources().getString(R.string.title_activity_reserve));
        adapter.addFrag(new InboxFragment(), getResources().getString(R.string.title_activity_inbox));
        adapter.addFrag(new AccountFragment(), getResources().getString(R.string.title_activity_account));

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
