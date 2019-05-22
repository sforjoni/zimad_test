package com.gimadeev.zimad_test.domain.interactor;

import com.gimadeev.zimad_test.data.PetRepository;
import com.gimadeev.zimad_test.data.model.DataRow;
import com.gimadeev.zimad_test.domain.FlowableUseCase;
import com.gimadeev.zimad_test.domain.executor.PostExecutionThread;

import java.util.List;

import io.reactivex.Flowable;

public class ListPetsUseCase extends FlowableUseCase<List<DataRow>, String> {

    private PetRepository repository;

    public ListPetsUseCase(PetRepository repository, PostExecutionThread postExecutionThread) {
        super(postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<List<DataRow>> buildFlowable(String type) {
        return repository.loadPets(type);
    }
}
