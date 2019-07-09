package com.example.semiproject_guru2.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semiproject_guru2.R;
import com.example.semiproject_guru2.bean.MemberBean;
import com.example.semiproject_guru2.bean.MemoBean;
import com.example.semiproject_guru2.database.FileDB;
import com.example.semiproject_guru2.fragment.FragmentCamera;
import com.example.semiproject_guru2.fragment.FragmentMemoWrite;
import com.google.android.material.tabs.TabLayout;

public class NewMemoActivity extends  AppCompatActivity {

    public NewMemoActivity(){}
    private TabLayout tabLayout2;  // Tab 영역
    private ViewPager viewPager2;  // 표시할 영역
    private MyPagerAdapter mMyPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);

        View.OnClickListener mBtnClick = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btnCancel:
                        finish();
                        break;
                    case R.id.btnSave:
                        //처리
                        saveProc();

                        break;
                }
            }
        };

        findViewById(R.id.btnCancel).setOnClickListener(mBtnClick);
        findViewById(R.id.btnSave).setOnClickListener(mBtnClick);

        tabLayout2 = findViewById(R.id.tabLayout2);
        viewPager2 = findViewById(R.id.viewPager2);

        //Tab 생성
        tabLayout2.addTab(tabLayout2.newTab().setText("글쓰기"));
        tabLayout2.addTab(tabLayout2.newTab().setText("사진찍기"));
        tabLayout2.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager 생성
        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),
                tabLayout2.getTabCount());
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
    //저장버튼 저장처리
    private void saveProc() {

        //1.첫번째 프래그먼트의 EditText 값을 받아온다.
        FragmentMemoWrite f0 = (FragmentMemoWrite)mMyPagerAdapter.instantiateItem(viewPager2,0);
        //2.두번째 프래그먼트의 mPhotoPath 값을 가져온다.
        FragmentCamera f1 = (FragmentCamera)mMyPagerAdapter.instantiateItem(viewPager2,1);

        EditText edtWriteMemo = f0.getView().findViewById(R.id.edtWriteMemo);
        String memoStr = edtWriteMemo.getText().toString();
        String photoPath = f1.mPhotoPath;

        Log.e("SEMI", "memoStr: " + memoStr + ", photoPath: " + photoPath);
        Toast.makeText(this, "memoStr: " + memoStr + ", photoPath: " + photoPath, Toast.LENGTH_LONG).show();

        //TODO 파일DB 에 저장처리
        MemoBean memobean = new MemoBean();
        memobean.memo = memoStr;
        memobean.memoPicPath = photoPath;
        MemberBean memberBean = FileDB.getLoginMember( this );
        FileDB.addMemo(this, memberBean.memId, memobean);
        finish();
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
