package com.example.projetpetitionnaire;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TroisiemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TroisiemeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText texteAge;
    private CheckBox checkBox;

    public TroisiemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TroisiemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TroisiemeFragment newInstance(String param1, String param2) {
        TroisiemeFragment fragment = new TroisiemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup )inflater.inflate(R.layout.fragment_troisieme, container, false);

        texteAge = rootView.findViewById(R.id.texteAge);
        checkBox = rootView.findViewById(R.id.checkBox);
        Ecouteur ec = new Ecouteur();
        checkBox.setOnCheckedChangeListener(ec);
        return rootView;
    }


    @Override
    public void onStop() {
        super.onStop();

        Membre.Builder m =((ConteneurFragmentsActivity) getActivity()).getM();


        if ( checkBox.isChecked())
            m.setAge(Integer.parseInt(texteAge.getText().toString()));


    }

    private class Ecouteur implements CompoundButton.OnCheckedChangeListener
    {



        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if ( b)
                texteAge.setEnabled(true);
            else
                texteAge.setEnabled(false);
        }
    }
}