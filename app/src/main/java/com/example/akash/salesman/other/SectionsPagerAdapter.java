package com.example.akash.salesman.other;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rkarthic on 15-08-2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter{
    int mNumOfTabs;
    ArrayList<String> tabName;
    ContentItem contentItem;

    @Override public Parcelable saveState() {
        Bundle bundle = (Bundle) super.saveState();
        if (bundle != null) {
            Parcelable[] states = bundle.getParcelableArray("states");
            bundle.putParcelableArray("states", null);
        } else bundle = new Bundle();
        return bundle;
    }
    List<ContentItem> contentItemList;

    public SectionsPagerAdapter(FragmentManager fm, int mNumOfTabs, ContentItem c, List<ContentItem> cList) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
        contentItem = c;
        contentItemList = cList;
    }

    @Override
    public Fragment getItem(int position) {
        TabbedContentFragment tabbedContentFragment=TabbedContentFragment.newInstance(contentItem);

        Bundle bundle = new Bundle();
        bundle.putInt("subTopic", position+1 );
        //This is for the call as part of the RANDOM HADITH fragment implementation
        if(contentItemList != null)
        {
            bundle.putString("itemName", contentItemList.get(position).getItem_name());
            bundle.putString("displayContent", contentItemList.get(position).getDisplay_content());
            bundle.putParcelable("itemImage", contentItemList.get(position).getItem_image());
            bundle.putString("contact", contentItemList.get(position).getContact());
            bundle.putString("owner", contentItemList.get(position).getOwner());

        }


        tabbedContentFragment.setArguments(bundle);
        return tabbedContentFragment;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return contentItemList.get(position).getItem_name();
    }
}
