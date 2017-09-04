package com.student.codemobile.myauthendemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class SuccessActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private UserBean mUserBean;
    private RecycleViewFragment mRecycleViewFragment;
    private FloatingActionMenu mFab_menu;
    private FloatingActionButton mFab_foods;
    private FloatingActionButton mFab_superhero;
    private FloatingActionButton mFab_songs;
    private FloatingActionButton mFab_training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // get inbound intent
        Intent inboundIntent = getIntent();
        mUserBean = (UserBean) inboundIntent.getParcelableExtra(UserBean.TABLE_NAME);
        Toast.makeText(this, "Rec: " + mUserBean.username, Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        bindWidgets();
        setEvents();

    }

    private void setEvents() {
        //ดัก Event ปั่มกดตัวลูก
        mFab_foods.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mFab_menu.close(true); //ปิดปุ่ม
                mRecycleViewFragment.feedData(RecycleViewFragment.kFeed_foods); //Feed Data ใหม่
            }
        });

        mFab_superhero.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mFab_menu.close(true);//ปิดปุ่ม
                mRecycleViewFragment.feedData(RecycleViewFragment.kFeed_superheros); //Feed Data ใหม่
            }
        });

        mFab_songs.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mFab_menu.close(true);//ปิดปุ่ม
                mRecycleViewFragment.feedData(RecycleViewFragment.kFeed_songs); //Feed Data ใหม่
            }
        });

        mFab_training.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mFab_menu.close(true);//ปิดปุ่ม
                mRecycleViewFragment.feedData(RecycleViewFragment.kFeed_trainings); //Feed Data ใหม่
            }
        });
    }

    private void bindWidgets() {
        mFab_menu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        mFab_menu.setClosedOnTouchOutside(true); //ถ้ากดตรง พื้นหลัง จะปิดหน้าปุ่มทั้งหมด ไม่ทำตรงอื่น

        mFab_foods = (FloatingActionButton) findViewById(R.id.fab_foods);
        mFab_superhero = (FloatingActionButton) findViewById(R.id.fab_superhero);
        mFab_songs = (FloatingActionButton) findViewById(R.id.fab_songs);
        mFab_training = (FloatingActionButton) findViewById(R.id.fab_training);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_success, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_success, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position) {
                case 0:
                    if (mRecycleViewFragment == null) {
                        mRecycleViewFragment = new RecycleViewFragment();
                    }
                    //ใส่ค่า mRecycleViewFragment.setArguments(UserBean) ตรงๆไม่ได้ ต้องแปลงก่อนส่ง
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(UserBean.TABLE_NAME, mUserBean);
                    mRecycleViewFragment.setArguments(bundle);
                    return mRecycleViewFragment;
                case 1:
                    return new GridFragment();
                case 2:
                    return new WebserviceFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "RecycleView";
                case 1:
                    return "GridView";
                case 2:
                    return "Webservice";
            }
            return null;
        }
    }
}
