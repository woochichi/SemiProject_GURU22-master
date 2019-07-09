package com.example.semiproject_guru2.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.semiproject_guru2.R;
import com.example.semiproject_guru2.bean.MemberBean;
import com.example.semiproject_guru2.bean.MemoBean;
import com.example.semiproject_guru2.database.FileDB;

public class FragmentMemoWrite extends Fragment {

    public FragmentMemoWrite() {
    }

    public FragmentMemoWrite(String text) {
        edtWriteMemo.setText(text);
    }

    public static TextView edtWriteMemo;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Fragment UI 생성
        View view = inflater.inflate(R.layout.fragment_memo_write, container, false);

        edtWriteMemo = view.findViewById(R.id.edtWriteMemo);

        long memoId = getActivity().getIntent().getLongExtra("memoId", -1);
        MemberBean memberBean = FileDB.getLoginMember( getActivity() );
        MemoBean memoBean = FileDB.getMemo(getActivity(), memberBean.memId, memoId );
        edtWriteMemo.setText(memoBean.memo);
        return view;
    }



}
