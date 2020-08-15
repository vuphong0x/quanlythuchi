package com.example.quanlythuchi.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.quanlythuchi.Adapter.ChiPagerAdapter;
import com.example.quanlythuchi.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ChiFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ChiPagerAdapter pagerAdapter;
    TabItem tabKhoanChi, tabLoaiChi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chi_fragment, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutChi);
        tabKhoanChi = view.findViewById(R.id.tabKhoanChi);
        tabLoaiChi = view.findViewById(R.id.tabLoaiChi);
        viewPager = view.findViewById(R.id.viewPager);
        pagerAdapter = new ChiPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return view;
    }
}
