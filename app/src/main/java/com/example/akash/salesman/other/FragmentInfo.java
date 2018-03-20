package com.example.akash.salesman.other;

import android.support.v4.app.Fragment;

/**
 * Created by rkarthic on 18/03/18.
 */

public class FragmentInfo {

    private Fragment fragment;
    private int navItemIndex;
    private String tag;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public int getNavItemIndex() {
        return navItemIndex;
    }

    public void setNavItemIndex(int navItemIndex) {
        this.navItemIndex = navItemIndex;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
