package tw.brad.sk_viewpagertest;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private FragmentManager fmgr;
    private Fragment[] pages = new Fragment[5];
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if (uri != null) {
            String value1 = uri.getQueryParameter("key1");
            String value2 = uri.getQueryParameter("key2");
            Log.v("brad", "key1 = " + value1);
            Log.v("brad", "key2 = " + value2);
        }


        viewPager = findViewById(R.id.viewPager);
        fmgr = getSupportFragmentManager();

        pages[0] = new Page0();
        pages[1] = new Page1();
        pages[2] = new Page2();
        pages[3] = new Page3();
        pages[4] = new Page4();

        initActionBar();

        MyAdapter myAdapter = new MyAdapter(fmgr);
        viewPager.setAdapter(myAdapter);
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0){
                    viewPager.setCurrentItem(1);
                }else if (position == 4){
                    viewPager.setCurrentItem(3);
                }else{
                    actionBar.setSelectedNavigationItem(position-1);
                }
            }
        });

        viewPager.setCurrentItem(1);

    }

    private void initActionBar(){
        MyTabListener myTabListener = new MyTabListener();
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(
                actionBar.newTab().setText("Page1")
                    .setTabListener(myTabListener)
        );
        actionBar.addTab(
                actionBar.newTab().setText("Page2")
                        .setTabListener(myTabListener)
        );
        actionBar.addTab(
                actionBar.newTab().setText("Page3")
                        .setTabListener(myTabListener)
        );
    }

    private class MyTabListener implements ActionBar.TabListener {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            viewPager.setCurrentItem(tab.getPosition()+1);
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

        }
    }



    private class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return pages[i];
        }

        @Override
        public int getCount() {
            return pages.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page" + position;
        }
    }

}
