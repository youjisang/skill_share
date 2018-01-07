package com.immymemine.kevin.skillshare.fragment.main_f;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.immymemine.kevin.skillshare.R;
import com.immymemine.kevin.skillshare.activity.SignInActivity;
import com.immymemine.kevin.skillshare.activity.SignUpActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineMeFragment extends Fragment {


    public OfflineMeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline_me, container, false);

        Context context = getActivity();

        view.findViewById(R.id.button_sign_in).setOnClickListener(
                v -> startActivity(new Intent(context, SignInActivity.class))
        );

        view.findViewById(R.id.button_sign_up).setOnClickListener(
                v -> startActivity(new Intent(context, SignUpActivity.class))
        );

        Log.d("JUWONLEE", "Fragment onCreateView");

        return view;
    }

}
