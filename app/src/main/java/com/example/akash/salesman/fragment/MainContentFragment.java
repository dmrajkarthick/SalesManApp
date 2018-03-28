package com.example.akash.salesman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akash.salesman.R;
import com.example.akash.salesman.other.CategoryPageItem;
import com.example.akash.salesman.other.ContentItem;
import com.example.akash.salesman.other.DBOperations;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMainContentFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainContentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static CategoryPageItem categoryPageItem;


    private OnMainContentFragmentInteractionListener mListener;

    public MainContentFragment() {
        // Required empty public constructor
    }

    public static MainContentFragment newInstance(CategoryPageItem cpItem) {
        categoryPageItem = cpItem;
        return new MainContentFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home_items_list_main, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.sectioned_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        DBOperations dbOperations = new DBOperations(getContext());
        dbOperations.open();
        List<ContentItem> contentItems = dbOperations.getMainContentPageData(categoryPageItem.getCategory_name());
        dbOperations.close();

        MainPageRecyclerViewAdapter adapter = new MainPageRecyclerViewAdapter(contentItems, mListener);
        recyclerView.setAdapter(adapter);


        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMainContentFragmentInteractionListener) {
            mListener = (OnMainContentFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMainContentFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnMainContentFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListFragmentInteractionListener(ContentItem item, List<ContentItem> contentItems);

        void onMenuItemClicklistener(ContentItem item);

        void onBookMarkofContent(ContentItem item);
    }
}
