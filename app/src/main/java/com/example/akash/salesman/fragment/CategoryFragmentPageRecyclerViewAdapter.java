package com.example.akash.salesman.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akash.salesman.R;
import com.example.akash.salesman.other.CategoryPageItem;

import java.util.List;

/**
 * Created by rkarthic on 15/03/18.
 */

class CategoryFragmentPageRecyclerViewAdapter extends RecyclerView.Adapter<CategoryFragmentPageRecyclerViewAdapter.CategoryItemHolder> {

    private final List<CategoryPageItem> mValues;
    private final CategoryPageFragment.OnCategoryFragmentInteractionListener mListener;
    private final Context context;

    public CategoryFragmentPageRecyclerViewAdapter(Context c, List<CategoryPageItem> categoryPageItemList, CategoryPageFragment.OnCategoryFragmentInteractionListener listener) {
        mValues = categoryPageItemList;
        mListener = listener;
        context = c;
    }

    @Override
    public CategoryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_category_item, parent, false);
        return new CategoryItemHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryItemHolder holder, int position) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String lang_pref = sharedPreferences.getString("lang_pref","Tamil");

        holder.bItem = mValues.get(position);
        holder.mBookNameView.setText(mValues.get(position).getCategory_name());
        holder.mBookNameView.setText(mValues.get(position).getDisplay_name());
        holder.mBookImageView.setImageBitmap(mValues.get(position).getCategoryImage());

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            if(null != mListener)
            {
                mListener.OnCategoryListFragmentInteractionListener(holder.bItem);
            }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class CategoryItemHolder extends RecyclerView.ViewHolder{

        public final View mView;
        public final ImageView mBookImageView;
        public final TextView mBookNameView;
        public CategoryPageItem bItem;

        public CategoryItemHolder(View view)
        {
            super(view);
            mView = view;
            mBookImageView = (ImageView)view.findViewById(R.id.bookImage);
            mBookNameView = (TextView) view.findViewById(R.id.tvBookName);
        }

    }
}
