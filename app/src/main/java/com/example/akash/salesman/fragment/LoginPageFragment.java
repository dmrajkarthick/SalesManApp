package com.example.akash.salesman.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.akash.salesman.R;
import com.example.akash.salesman.other.DBOperations;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginPageFragment extends Fragment {

    private EditText editText, password;
    private Button login, signup;


    private OnFragmentInteractionListener mListener;

    public LoginPageFragment() {
        // Required empty public constructor
    }


    public static LoginPageFragment newInstance() {
        LoginPageFragment fragment = new LoginPageFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loginpage, container, false);
        editText = (EditText)view.findViewById(R.id.userID);
        password = (EditText) view.findViewById(R.id.password);

        login = (Button)view.findViewById(R.id.login);
        signup = (Button)view.findViewById(R.id.signup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBOperations dbOperations = new DBOperations(getContext());
                dbOperations.open();

                dbOperations.close();


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



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
//        if (context instanceof OnCategoryFragmentInteractionListener) {
//            mListener = (OnCategoryFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnCategoryFragmentInteractionListener");
//        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
