package com.example.projetpetitionnaire;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FinFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button boutonSauv;

    public FinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FinFragment newInstance(String param1, String param2) {
        FinFragment fragment = new FinFragment();
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
        ViewGroup rootView = (ViewGroup )inflater.inflate(R.layout.fragment_fin, container, false);

        boutonSauv = rootView.findViewById(R.id.boutonSauvegarder);
        Ecouteur ec = new Ecouteur();
        boutonSauv.setOnClickListener(ec);
        return rootView;
    }

    private class Ecouteur implements View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            // sauvegarder ds fichier de sérialisation
            Membre membre = ((ConteneurFragmentsActivity)getActivity()).getM().build();
            try(
            FileOutputStream fos = getActivity().openFileOutput("fichier.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
                )
            {
                oos.writeObject(membre);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}