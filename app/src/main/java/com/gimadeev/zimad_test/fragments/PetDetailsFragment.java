package com.gimadeev.zimad_test.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.gimadeev.zimad_test.R;
import com.gimadeev.zimad_test.databinding.FragmentPetDetailsBinding;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

public abstract class PetDetailsFragment extends Fragment {

    private FragmentPetDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setPet(getPet());
    }

    protected abstract BindingPet getPet();
}
