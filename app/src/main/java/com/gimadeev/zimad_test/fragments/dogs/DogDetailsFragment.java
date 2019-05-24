package com.gimadeev.zimad_test.fragments.dogs;

import com.gimadeev.zimad_test.fragments.PetDetailsFragment;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

public class DogDetailsFragment extends PetDetailsFragment {

    @Override
    protected BindingPet getPet() {
        return DogDetailsFragmentArgs.fromBundle(getArguments()).getDog();
    }
}
