package com.davidhoeck.firechat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidhoeck.firechat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View fView;
    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileEmail;

    private FirebaseAuth mAuth;

    public ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       fView = inflater.inflate(R.layout.fragment_profile, container, false);

        mProfileImage = (ImageView) fView.findViewById(R.id.profileImage);
        mProfileName = (TextView) fView.findViewById(R.id.profileName);
        mProfileEmail = (TextView) fView.findViewById(R.id.profileEmail);

        mAuth = FirebaseAuth.getInstance();

        Picasso.with(getActivity()).load(mAuth.getCurrentUser().getPhotoUrl().toString()).into(mProfileImage);
        mProfileName.setText(mAuth.getCurrentUser().getDisplayName());
        mProfileEmail.setText(mAuth.getCurrentUser().getEmail());

        return fView;
    }

}
