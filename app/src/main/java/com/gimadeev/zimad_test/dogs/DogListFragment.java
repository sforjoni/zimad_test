package com.gimadeev.zimad_test.dogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gimadeev.zimad_test.PetAdapter;
import com.gimadeev.zimad_test.R;
import com.gimadeev.zimad_test.presentaion.PetListViewModel;
import com.gimadeev.zimad_test.presentaion.PetVmFactory;
import com.gimadeev.zimad_test.presentaion.ViewState;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

import java.util.List;

public class DogListFragment extends Fragment {

    private PetListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, new PetVmFactory()).get(PetListViewModel.class);

        viewModel.getState().observe(this, new Observer<ViewState<List<BindingPet>>>() {
            @Override
            public void onChanged(ViewState<List<BindingPet>> viewState) {
                switch (viewState.status) {
                    case SUCCESS:
                        updateList(viewState.data);
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), R.string.error_load_pets, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.loadPets("dog");
    }

    private void updateList(List<BindingPet> pets) {
        RecyclerView list = getView().findViewById(R.id.rvPets);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));
        list.setAdapter(new PetAdapter(pets, new PetAdapter.OnPetClick() {
            @Override
            public void onClick(BindingPet pet) {
                showDetails(pet);
            }
        }));
    }

    private void showDetails(BindingPet pet) {

    }
}
