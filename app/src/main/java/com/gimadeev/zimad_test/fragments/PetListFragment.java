package com.gimadeev.zimad_test.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gimadeev.zimad_test.R;
import com.gimadeev.zimad_test.presentaion.PetListViewModel;
import com.gimadeev.zimad_test.presentaion.PetVmFactory;
import com.gimadeev.zimad_test.presentaion.ViewState;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

import java.util.List;

public abstract class PetListFragment extends Fragment {

    private PetListViewModel viewModel;

    private RecyclerView list;
    private Parcelable listState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new PetVmFactory()).get(PetListViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = view.findViewById(R.id.rvPets);
        list.setLayoutManager(new LinearLayoutManager(requireContext()));
        list.setAdapter(new PetAdapter(new PetAdapter.OnPetClick() {
            @Override
            public void onClick(BindingPet pet) {
                showDetails(pet);
            }
        }));

        viewModel.loadPets(getPetType()).observe(this, new Observer<ViewState<List<BindingPet>>>() {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listState != null) {
            list.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        listState = list.getLayoutManager().onSaveInstanceState();
    }

    private void updateList(List<BindingPet> pets) {
        PetAdapter adapter = (PetAdapter) list.getAdapter();
        adapter.setPets(pets);
        adapter.notifyDataSetChanged();
    }

    private void showDetails(BindingPet pet) {
        NavHostFragment.findNavController(this).navigate(getDetailsAction(pet));
    }

    protected abstract String getPetType();

    protected abstract NavDirections getDetailsAction(BindingPet pet);
}
