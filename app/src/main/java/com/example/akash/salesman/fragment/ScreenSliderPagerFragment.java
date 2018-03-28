package com.example.akash.salesman.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akash.salesman.R;
import com.example.akash.salesman.other.ContentItem;
import com.example.akash.salesman.other.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScreenSliderPagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScreenSliderPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScreenSliderPagerFragment extends Fragment {
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ArrayList<String> tabName;
    private static ContentItem contentItem;
    private static List<ContentItem> contentItemList;

    private OnFragmentInteractionListener mListener;

    public ScreenSliderPagerFragment() {
        // Required empty public constructor
    }


    public static ScreenSliderPagerFragment newInstance(ContentItem c, List<ContentItem> cList) {
        ScreenSliderPagerFragment fragment = new ScreenSliderPagerFragment();
        /*Bundle args = new Bundle();
        args.putString(Constants.FRAG_A, contentItem);
        fragment.setArguments(args);*/
        contentItem = c;
        contentItemList = cList;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_screen_slider_pager, container, false);
        mViewPager = (ViewPager)view.findViewById(R.id.container);
        tabName=new ArrayList<String>();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), getNumberofSubTopicsInSection(), contentItem, contentItemList);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(getItemPosition());
        //mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), getNumberofSubTopicsInSection(), contentItem, contentItemList);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(getItemPosition());

    }


    public int getNumberofSubTopicsInSection()
    {
        if(contentItemList == null)
        {
            return 1;
        }
        return contentItemList.size();
    }
    public int getItemPosition() {
        int i=0;
        for(ContentItem c : contentItemList)
        {
            if(c == contentItem) return i;
            else i++;
        }
        return 0;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
