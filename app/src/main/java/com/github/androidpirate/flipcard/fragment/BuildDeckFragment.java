package com.github.androidpirate.flipcard.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.androidpirate.flipcard.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuildDeckFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuildDeckFragment extends Fragment {
    private EditText mTitle;
    private EditText mCategory;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    public BuildDeckFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BuildDeckFragment.
     */
    public static BuildDeckFragment newInstance() {
        return new BuildDeckFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_build_deck, container, false);
        setViews(view);
        // Inflate the layout for this fragment
        return view;
    }

    private void setViews(View view) {
        mTitle = view.findViewById(R.id.et_title);
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCategory = view.findViewById(R.id.et_category);
        mCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRecyclerView = view.findViewById(R.id.rv_card_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFab = view.findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
