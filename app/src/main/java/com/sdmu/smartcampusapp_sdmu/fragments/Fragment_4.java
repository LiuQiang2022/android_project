package com.sdmu.smartcampusapp_sdmu.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import com.sdmu.smartcampusapp_sdmu.R;
import com.sdmu.smartcampusapp_sdmu.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_4 extends Fragment {


    private static final int CHOOSE_PICTURE = 0;
    private static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private SharedPreferences sp;
    private View rootView;
    @BindView(R.id.rl_logout)
    RelativeLayout rl_logout;
    @BindView(R.id.my_info)
    RelativeLayout my_info;
    @BindView(R.id.update_code)
    RelativeLayout update_code;
    @BindView(R.id.about_us)
    RelativeLayout about_us;
    private String mParam1;
    private String mParam2;

    public Fragment_4() {
    }


    public static Fragment_4 newInstance() {
        Fragment_4 fragment = new Fragment_4();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(R.layout.fragment_4, container, false);
        ButterKnife.bind(this, rootView);
        initListener(rootView);
        return rootView;
    }

    private void initListener(View rootView) {
        sp = getActivity().getSharedPreferences("sp_login", Context.MODE_PRIVATE);
        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.edit().putBoolean("is_auto", false).commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        my_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), MyInfo.class));
            }
        });
        update_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), UpdatePwdActivity.class));
            }
        });
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}