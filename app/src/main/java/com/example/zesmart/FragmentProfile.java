package com.example.zesmart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zesmart.R;
import com.example.zesmart.api.Profile;

public class FragmentProfile extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Profile profile = new Profile(getActivity());
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        profile.ProfileDetail(view);
        return view;
    }
}
