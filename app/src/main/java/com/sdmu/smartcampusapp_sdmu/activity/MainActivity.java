package com.sdmu.smartcampusapp_sdmu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.sdmu.smartcampusapp_sdmu.R;
import com.sdmu.smartcampusapp_sdmu.adapter.MainPagerAdapter;
import com.sdmu.smartcampusapp_sdmu.fragments.Fragment_4;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.vp_fragments)
    ViewPager2 viewPager2;
    @BindView(R.id.ll_firstpage)
    LinearLayout ll_firstpager;
    @BindView(R.id.ll_application)
    LinearLayout ll_application;
    @BindView(R.id.ll_myclass)
    LinearLayout ll_myclass;
    @BindView(R.id.ll_mine)
    LinearLayout ll_mine;
    @BindView(R.id.iv_application)
    ImageView iv_application;
    @BindView(R.id.iv_firstpage)
    ImageView iv_firstpager;
    @BindView(R.id.iv_myclass)
    ImageView iv_myclass;
    @BindView(R.id.iv_mine)
    ImageView iv_mine;
    private ImageView iv_current;
    @BindView(R.id.tb_first)
    Toolbar tb_first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPager();
        initTabView();
    }

    private void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(BlankFragment.newInstance("首页"));
        fragmentList.add(BlankFragment.newInstance("应用"));
        fragmentList.add(BlankFragment.newInstance("班级"));
        fragmentList.add(Fragment_4.newInstance());

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), getLifecycle(), fragmentList);
        viewPager2.setAdapter(mainPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);//可设置滑动效果
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

    }

    private void initTabView() {
        iv_firstpager.setSelected(true);//设置进入首页时第一个位置图片默认被选中
        iv_current = iv_firstpager;
    }

    @OnClick({R.id.ll_firstpage, R.id.ll_myclass, R.id.ll_mine, R.id.ll_application})
    public void onClickLayout(View view) {
        changeTab(view.getId());
    }

    private void changeTab(int position) {
        iv_current.setSelected(false);
        switch (position) {
            case R.id.ll_firstpage:
                viewPager2.setCurrentItem(0);
            case 0:
                iv_firstpager.setSelected(true);
                iv_current = iv_firstpager;
                break;
            case R.id.ll_application:
                viewPager2.setCurrentItem(1);
            case 1:
                iv_application.setSelected(true);
                iv_current = iv_application;
                break;
            case R.id.ll_myclass:
                viewPager2.setCurrentItem(2);
            case 2:
                iv_myclass.setSelected(true);
                iv_current = iv_myclass;
                break;
            case R.id.ll_mine:
                viewPager2.setCurrentItem(3);
            case 3:
                iv_mine.setSelected(true);
                iv_current = iv_mine;
                break;
        }
    }
}