
package com.example.semiproject_guru2.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiproject_guru2.R;
import com.example.semiproject_guru2.activity.MemoDetailActivity;
import com.example.semiproject_guru2.activity.ModifyMemoActivity;
import com.example.semiproject_guru2.activity.NewMemoActivity;
import com.example.semiproject_guru2.bean.MemberBean;
import com.example.semiproject_guru2.bean.MemoBean;
import com.example.semiproject_guru2.database.FileDB;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentMemo extends Fragment {

    public static ListView mLstMemo;
    public static final int SAVE = 1001;
    // 원본 데이터
    List<MemoBean> memoList = new ArrayList<>();
    // Adapter 생성 및 적용
    ListAdapter adapter;

    public FragmentMemo() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Fragment UI 생성
        View view = inflater.inflate(R.layout.fragment_memo, container, false);

        Button btnNewMemo = view.findViewById(R.id.btnNewMemo);
        btnNewMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //새메모 화면으로 이동
                Intent intent = new Intent(view.getContext(), NewMemoActivity.class);
                startActivity(intent);
            }
        });

        mLstMemo = view.findViewById(R.id.lstMemo);

        return view;  // 완성된 UI 리턴
    }

    @Override
    public void onResume() {
        super.onResume();

        MemberBean memberBean = FileDB.getLoginMember(getActivity());
        memoList = FileDB.getMemoList(getActivity(), memberBean.memId);
        adapter = new ListAdapter(memoList, getActivity());
        mLstMemo.setAdapter(adapter);
    }

    class ListAdapter extends BaseAdapter {
        List<MemoBean> items;  //원본 데이터
        Context mContext;
        LayoutInflater inflater;

        public ListAdapter(List<MemoBean> items, Context context) {
            this.items = items;
            this.mContext = context;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        public void setItems(List<MemoBean> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            // view_item.xml 획득
            view = inflater.inflate(R.layout.view_item, null);

            // 객체 획득
            ImageView imgView = view.findViewById(R.id.imageView);
            final TextView txtMemo = view.findViewById(R.id.txt_memo);
            Button btnDel = view.findViewById(R.id.btnDel);
            Button btnDetail = view.findViewById(R.id.btnDetail);
            Button btnRevise = view.findViewById(R.id.btnRevise);

            // 원본에서 i번째 Item 획득
            final MemoBean item = items.get(i);

            // 원본 데이터를 UI에 적용
            if(item.memoPicPath != null) {
                imgView.setImageURI( Uri.fromFile(new File(item.memoPicPath)));
            }
            txtMemo.setText(item.memo);

            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MemberBean memberBean = FileDB.getLoginMember(getActivity());
                    FileDB.delMemo(getActivity(), memberBean.memId, item.memoID);

                    items = FileDB.getMemoList(getActivity(), memberBean.memId);
                    notifyDataSetChanged(); //갱신해라
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), MemoDetailActivity.class);
                    i.putExtra("memoId", item.memoID);
                    i.putExtra("memoPicPath", item.memoPicPath);
                    startActivity(i);
                }

            });

            btnRevise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), ModifyMemoActivity.class);
                    i.putExtra("memoId", item.memoID);
                    i.putExtra("memoPicPath", item.memoPicPath);
                    startActivity(i);
                }
            });

            return view;  // 완성된 UI 리턴
        }
    }
}