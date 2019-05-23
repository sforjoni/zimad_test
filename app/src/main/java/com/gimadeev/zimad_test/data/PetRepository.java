package com.gimadeev.zimad_test.data;

import com.gimadeev.zimad_test.data.model.DataPet;

import java.util.List;

import io.reactivex.Maybe;

public interface PetRepository {

    Maybe<List<DataPet>> loadPets(String type);
}
