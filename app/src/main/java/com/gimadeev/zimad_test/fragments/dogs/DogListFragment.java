package com.gimadeev.zimad_test.fragments.dogs;

import androidx.navigation.NavDirections;

import com.gimadeev.zimad_test.fragments.PetListFragment;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

public class DogListFragment extends PetListFragment {

    @Override
    protected String getPetType() {
        return "dog";
    }

    @Override
    protected NavDirections getDetailsAction(BindingPet pet) {
        return DogListFragmentDirections.actionDogListFragmentToDogDetailsFragment(pet);
    }
}
