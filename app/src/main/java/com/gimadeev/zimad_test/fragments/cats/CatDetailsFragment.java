package com.gimadeev.zimad_test.fragments.cats;

import com.gimadeev.zimad_test.fragments.PetDetailsFragment;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

public class CatDetailsFragment extends PetDetailsFragment {

    @Override
    protected BindingPet getPet() {
        return CatDetailsFragmentArgs.fromBundle(getArguments()).getCat();
    }
}
