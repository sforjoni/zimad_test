package com.gimadeev.zimad_test.presentaion;

import com.gimadeev.zimad_test.data.PetRepository;
import com.gimadeev.zimad_test.domain.interactor.ListPetsUseCase;
import com.gimadeev.zimad_test.presentaion.executor.UiThread;
import com.gimadeev.zimad_test.retrofit.RetrofitRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PetVmFactory extends ViewModelProvider.NewInstanceFactory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        PetRepository petRepository = new RetrofitRepository();

        return (T) new PetListViewModel(new ListPetsUseCase(petRepository, new UiThread()));
        //return super.create(modelClass);
    }
}
