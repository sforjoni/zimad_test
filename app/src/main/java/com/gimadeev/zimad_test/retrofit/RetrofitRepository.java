package com.gimadeev.zimad_test.retrofit;

import com.gimadeev.zimad_test.data.PetRepository;
import com.gimadeev.zimad_test.data.model.DataPet;

import java.util.List;

import io.reactivex.Maybe;

public class RetrofitRepository implements PetRepository {

    @Override
    public Maybe<List<DataPet>> loadPets(String type) {
        return RetrofitClient.getInstance().loadPets(type);
    }
}
