package com.gimadeev.zimad_test.presentaion;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gimadeev.zimad_test.data.model.DataPet;
import com.gimadeev.zimad_test.domain.interactor.ListPetsUseCase;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;
import com.gimadeev.zimad_test.presentaion.binding.PetConverter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PetListViewModel extends ViewModel implements LifecycleObserver {

    private ListPetsUseCase listPetsUseCase;
    private MutableLiveData<ViewState<List<BindingPet>>> state = new MutableLiveData();

    public PetListViewModel(ListPetsUseCase listPetsUseCase) {
        this.listPetsUseCase = listPetsUseCase;
    }

    public LiveData<ViewState<List<BindingPet>>> loadPets(String type) {
        if (state.getValue() == null) {
            state.postValue(new ViewState(ViewState.Status.LOADING));
            listPetsUseCase.execute(type,
               new Consumer<List<DataPet>>() {
                @Override
                public void accept(List<DataPet> dataPets) throws Exception {
                    List<BindingPet> pets = new ArrayList<>(dataPets.size());
                    for (DataPet dataPet : dataPets ) {
                        pets.add(PetConverter.fromData(dataPet));
                    }
                    state.postValue(new ViewState(ViewState.Status.SUCCESS, pets));
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    state.postValue(new ViewState(ViewState.Status.ERROR, throwable));
                }
            });
        }

        return state;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        listPetsUseCase.dispose();
    }
}
