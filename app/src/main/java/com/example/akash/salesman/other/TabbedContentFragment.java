package com.example.akash.salesman.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.akash.salesman.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabbedContentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabbedContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabbedContentFragment extends Fragment {

    private TextView text_view_for_tab_selection, text_view_for_english_content, contact, owner;
    private ImageView itemImage;
    private OnFragmentInteractionListener mListener;
    static ContentItem contentItem;

    public TabbedContentFragment() {
        // Required empty public constructor
    }

    //public static TabbedContentFragment newInstance(String tabSelected, ContentItem c) {
    public static TabbedContentFragment newInstance(ContentItem c) {
        TabbedContentFragment fragment = new TabbedContentFragment();
        contentItem =c;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.contentBookmark:
                if(item.isChecked()){
                    item.setChecked(false);
                    item.setIcon(R.drawable.ic_menu_gallery);
                }
                else {
                    item.setChecked(true);
                    item.setIcon(R.drawable.ic_menu_bookmark);
                }
                DBOperations dbOperations = new DBOperations(getContext());
                dbOperations.open();
                dbOperations.bookMarkContent(contentItem);
                dbOperations.close();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        DBOperations dbOperations = new DBOperations(getContext());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tabbed_content, container, false);
        text_view_for_tab_selection=(TextView)view.findViewById(R.id.text_view_for_tab_selection);
        text_view_for_english_content = (TextView)view.findViewById(R.id.text_view_for_english_content);
        contact = (TextView)view.findViewById(R.id.contact);
        owner = (TextView) view.findViewById(R.id.owner);
        itemImage = (ImageView) view.findViewById(R.id.imageView);
        Bundle bundle = getArguments();
        String contentTamil = bundle.getString("itemName");
        String contentEnglish = bundle.getString("displayContent");
        Bitmap itemImage2 = bundle.getParcelable("itemImage");
        String contact1 = bundle.getString("contact");
        String owner1 = bundle.getString("owner");
        text_view_for_tab_selection.setText(contentTamil);
        text_view_for_english_content.setText("Description: \n" + contentEnglish);
        itemImage.setImageBitmap(itemImage2);
        owner.setText("Contact: " +owner1);
        contact.setText("Owner: "+contact1);
        return view;
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
    public interface    OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
