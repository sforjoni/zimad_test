package com.gimadeev.zimad_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gimadeev.zimad_test.databinding.ItemPetBinding;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder>{

    private List<BindingPet> pets;
    private OnPetClick onPetClickListener;

    public PetAdapter(OnPetClick onPetClickListener) {
        this.pets = Collections.emptyList();
        this.onPetClickListener = onPetClickListener;
    }

    public void setPets(List<BindingPet> pets) {
        this.pets = pets;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        ItemPetBinding binding = holder.getBinding();
        final BindingPet currentPet = pets.get(position);
        binding.setPet(currentPet);
        binding.executePendingBindings();
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPetClickListener.onClick(currentPet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public interface OnPetClick {
        void onClick(BindingPet pet);
    }

    public class PetViewHolder extends RecyclerView.ViewHolder {

        private ItemPetBinding binding;

        public PetViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }

        public ItemPetBinding getBinding() {
            return binding;
        }
    }
}
