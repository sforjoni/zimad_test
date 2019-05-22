package com.gimadeev.zimad_test.presentaion;

import com.gimadeev.zimad_test.data.model.DataRow;
import com.gimadeev.zimad_test.domain.interactor.ListPetsUseCase;
import com.gimadeev.zimad_test.presentaion.binding.BindingPet;
import com.gimadeev.zimad_test.presentaion.binding.PetConverter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;
import io.reactivex.functions.Consumer;

public class PetListViewModel extends ViewModel implements LifecycleObserver {

    private ListPetsUseCase listPetsUseCase;
    private MutableLiveData<ViewState<List<BindingPet>>> state = new MutableLiveData();

    public PetListViewModel(ListPetsUseCase listPetsUseCase) {
        this.listPetsUseCase = listPetsUseCase;
    }

    public LiveData<ViewState<List<BindingPet>>> getState() {
        return state;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void loadPets(String type) {
        if (state.getValue() == null) {
            state.postValue(new ViewState(ViewState.Status.LOADING));
            listPetsUseCase.execute(type,
               new Consumer<List<DataRow>>() {
                @Override
                public void accept(List<DataRow> dataRows) throws Exception {
                    List<BindingPet> rows = new ArrayList<>(dataRows.size());
                    for (int i = 0; i < rows.size(); ++i) {
                        rows.add(PetConverter.fromData(dataRows.get(i)));
                    }
                    state.postValue(new ViewState(ViewState.Status.SUCCESS, rows));
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    state.postValue(new ViewState(ViewState.Status.ERROR, throwable));
                }
            },
                    null);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        listPetsUseCase.dispose();
    }
}
