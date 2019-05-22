package com.gimadeev.zimad_test.retrofit;

import com.gimadeev.zimad_test.data.PetRepository;
import com.gimadeev.zimad_test.data.model.DataRow;

import java.util.List;

import io.reactivex.Flowable;

public class RetrofitRepository implements PetRepository {

    @Override
    public Flowable<List<DataRow>> loadPets(String type) {
        return RetrofitClient.getInstance().loadPets(type);
    }
}
