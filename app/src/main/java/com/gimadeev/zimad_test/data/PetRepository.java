package com.gimadeev.zimad_test.data;

import com.gimadeev.zimad_test.data.model.DataRow;

import java.util.List;

import io.reactivex.Flowable;

public interface PetRepository {

    Flowable<List<DataRow>> loadPets(String type);
}
