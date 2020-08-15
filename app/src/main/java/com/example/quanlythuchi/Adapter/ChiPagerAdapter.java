package com.example.quanlythuchi.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quanlythuchi.TabPager.KhoanChiFragment;
import com.example.quanlythuchi.TabPager.KhoanThuFragment;
import com.example.quanlythuchi.TabPager.LoaiChiFragment;
import com.example.quanlythuchi.TabPager.LoaiThuFragment;

public class ChiPagerAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public ChiPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KhoanChiFragment();
            case 1:
                return new LoaiChiFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
