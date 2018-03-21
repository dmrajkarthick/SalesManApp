package com.example.akash.salesman.other;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akash.salesman.R;
import com.example.akash.salesman.fragment.BookMarksFragment;

import java.util.List;

/**
 * Created by rkarthic on 21/03/18.
 */

public class BookMarksPageRecyclerViewAdapter extends RecyclerView.Adapter<BookMarksPageRecyclerViewAdapter.ItemViewHolder> {

    private final List<ContentItem> mValues;
    private final BookMarksFragment.OnBookMarksFragmentInteractionListener mListener;
    Toolbar toolbar;

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mMainContentView;
        public final ImageView mItemImageView;
        public ContentItem mItem;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            mMainContentView =(TextView)view.findViewById(R.id.maincontent);
            mItemImageView = (ImageView)view.findViewById(R.id.imageView);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public BookMarksPageRecyclerViewAdapter(List<ContentItem> items, BookMarksFragment.OnBookMarksFragmentInteractionListener listener) {

        mValues = items;
        mListener = listener;
    }

    @Override
    public BookMarksPageRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_items, parent, false);
        toolbar = (Toolbar)view.findViewById(R.id.card_toolbar);
        toolbar.inflateMenu(R.menu.card_toolbar);
        return new BookMarksPageRecyclerViewAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BookMarksPageRecyclerViewAdapter.ItemViewHolder holder, int position) {

        int bmValue = mValues.get(position).getBookmark();
        if(bmValue == 1) {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(R.menu.bookmark_card_toolbar);
        }
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getItem_name());
        holder.mMainContentView.setText(mValues.get(position).getDisplay_content());
        holder.mItemImageView.setImageBitmap(mValues.get(position).getItem_image());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.OnListFragmentInteractionListener(holder.mItem, mValues);
                }
            }
        });

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.contentShare:
                                mListener.onMenuItemClicklistener(holder.mItem);
                                return true;
                            case R.id.contentBookmark:
                                if(item.isChecked()){
                                    item.setChecked(false);
                                    item.setIcon(R.drawable.ic_menu_bookmark);
                                }
                                else {
                                    item.setChecked(true);
                                    item.setIcon(R.drawable.ic_menu_bookmark);
                                }
                                mListener.onBookMarkofContent(holder.mItem);
                                return true;
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    public int getItemCount() { return mValues.size(); }

}
