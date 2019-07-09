package com.example.semiproject_guru2.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.semiproject_guru2.fragment.FragmentMember;
import com.example.semiproject_guru2.fragment.FragmentMemo;
import com.example.semiproject_guru2.R;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;  // Tab 영역
    private ViewPager viewPager;  // 표시할 영역

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        //Tab 생성
        tabLayout.addTab(tabLayout.newTab().setText("메모"));
        tabLayout.addTab(tabLayout.newTab().setText("회원정보"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager 생성
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    static class MyPagerAdapter extends FragmentPagerAdapter {
        int tabSize;  // TAB 수

        public MyPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.tabSize = count;  // 탭의 수
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FragmentMemo();
                case 1:
                    return new FragmentMember();
            }

            return null;
        }

        @Override
        public int getCount() {
            return this.tabSize;
        }
    }
}