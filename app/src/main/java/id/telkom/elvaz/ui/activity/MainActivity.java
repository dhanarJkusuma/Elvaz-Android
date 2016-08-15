package id.telkom.elvaz.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import id.telkom.elvaz.R;
import id.telkom.elvaz.database.TownsDatabase;
import id.telkom.elvaz.model.EarthStationInformation;
import id.telkom.elvaz.ui.fragments.LoginFragment;
import id.telkom.elvaz.ui.fragments.UserFragment;


public class MainActivity extends AppCompatActivity
{
    @Bind(R.id.tabsLayout)
    TabLayout tabsLayout;

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TownsDatabase db = new TownsDatabase(this);


        ButterKnife.bind(this);
        settingViewPager(viewPager);
        tabsLayout.setupWithViewPager(viewPager);

    }

    private void settingViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addTabs(new UserFragment(),"Report");
        adapter.addTabs(new LoginFragment(),"Admin");
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addTabs(Fragment fragment,String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
