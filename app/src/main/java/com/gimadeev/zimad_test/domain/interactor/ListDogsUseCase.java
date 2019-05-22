package com.gimadeev.zimad_test.domain.interactor;

import com.gimadeev.zimad_test.data.PetRepository;
import com.gimadeev.zimad_test.data.model.DataRow;
import com.gimadeev.zimad_test.domain.FlowableUseCase;
import com.gimadeev.zimad_test.domain.executor.PostExecutionThread;

import java.util.List;

import io.reactivex.Flowable;

public class ListDogsUseCase extends FlowableUseCase<List<DataRow>> {

    private PetRepository repository;

    public ListDogsUseCase(PetRepository repository, PostExecutionThread postExecutionThread) {
        super(postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<List<DataRow>> buildFlowable() {
        return repository.loadDogs();
    }
}
