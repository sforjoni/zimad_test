package com.gimadeev.zimad_test.domain.interactor;

import com.gimadeev.zimad_test.data.PetRepository;
import com.gimadeev.zimad_test.data.model.DataPet;
import com.gimadeev.zimad_test.domain.MaybeUseCase;
import com.gimadeev.zimad_test.domain.executor.PostExecutionThread;

import java.util.List;

import io.reactivex.Maybe;

public class ListPetsUseCase extends MaybeUseCase<List<DataPet>, String> {

    private PetRepository repository;

    public ListPetsUseCase(PetRepository repository, PostExecutionThread postExecutionThread) {
        super(postExecutionThread);
        this.repository = repository;
    }

    @Override
    protected Maybe<List<DataPet>> buildMaybe(String type) {
        return repository.loadPets(type);
    }
}
