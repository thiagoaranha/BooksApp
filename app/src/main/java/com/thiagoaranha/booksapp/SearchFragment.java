package com.thiagoaranha.booksapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Thiago on 03/08/17.
 */

public class SearchFragment extends Fragment {
    View root;
    OnSubmitSearchListener onSubmitSearchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        root.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                TextView searchText = (TextView)root.findViewById(R.id.txt_search);
                onSubmitSearchListener.onSubmitSearch(searchText.getText().toString());
            }
        });

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onSubmitSearchListener = (OnSubmitSearchListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSubmitSearchListener");
        }
    }

    public interface OnSubmitSearchListener{
        public void onSubmitSearch(String text);
    }

}
