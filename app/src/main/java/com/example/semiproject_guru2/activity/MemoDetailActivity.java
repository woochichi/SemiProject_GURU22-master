package com.example.semiproject_guru2.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.semiproject_guru2.R;
import com.example.semiproject_guru2.fragment.FragmentCamera;
import com.example.semiproject_guru2.fragment.FragmentMemo;
import com.example.semiproject_guru2.fragment.FragmentMemoWrite;
import com.google.android.material.tabs.TabLayout;

public class MemoDetailActivity extends AppCompatActivity {

    private TabLayout tabLayout2;  // Tab 영역
    private ViewPager viewPager2;  // 표시할 영역
    private MyPagerAdapter mMyPagerAdapter;
    public long memoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_detail);

        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tabLayout2 = findViewById(R.id.tabLayout2);
        viewPager2 = findViewById(R.id.viewPager2);

        //Tab 생성
        tabLayout2.addTab(tabLayout2.newTab().setText("글쓰기"));
        tabLayout2.addTab(tabLayout2.newTab().setText("사진찍기"));
        tabLayout2.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager 생성
        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), tabLayout2.getTabCount());
        viewPager2.setAdapter(mMyPagerAdapter);
        viewPager2.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout2));
        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        int tabSize;  // TAB 수

        public MyPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.tabSize = count;  // 탭의 수
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FragmentMemoWrite();
                case 1:
                    return new FragmentCamera();
            }

            return null;
        }

        @Override
        public int getCount() {
            return this.tabSize;
        }
    }
}



