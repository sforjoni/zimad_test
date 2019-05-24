package com.gimadeev.zimad_test.fragments.cats;

import androidx.navigation.NavDirections;

import com.gimadeev.zimad_test.fragments.PetListFragment;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

public class CatListFragment extends PetListFragment {

    @Override
    protected String getPetType() {
        return "cat";
    }

    @Override
    protected NavDirections getDetailsAction(BindingPet pet) {
        return CatListFragmentDirections.actionCatListFragmentToCatDetailsFragment(pet);
    }
}
