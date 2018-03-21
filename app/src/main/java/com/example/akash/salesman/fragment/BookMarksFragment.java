package com.example.akash.salesman.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akash.salesman.R;
import com.example.akash.salesman.other.BookMarksPageRecyclerViewAdapter;
import com.example.akash.salesman.other.ContentItem;
import com.example.akash.salesman.other.DBOperations;

import java.util.List;


public class BookMarksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;



    private OnBookMarksFragmentInteractionListener mListener;

    public BookMarksFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BookMarksFragment newInstance(int columnCount) {
        BookMarksFragment fragment = new BookMarksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        DBOperations dbOperations = new DBOperations(getContext());
        dbOperations.open();
        List<ContentItem> contentItems = dbOperations.getBookMarkedData();
        dbOperations.close();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MainPageRecyclerViewAdapter(ContentHolder.ITEMS, mListener, newContext));
            recyclerView.setAdapter(new BookMarksPageRecyclerViewAdapter(contentItems, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookMarksFragmentInteractionListener) {
            mListener = (OnBookMarksFragmentInteractionListener) context;
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
    public interface OnBookMarksFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnListFragmentInteractionListener(ContentItem item, List<ContentItem> contentItems);

        void onMenuItemClicklistener(ContentItem item);

        void onBookMarkofContent(ContentItem item);
    }
}
