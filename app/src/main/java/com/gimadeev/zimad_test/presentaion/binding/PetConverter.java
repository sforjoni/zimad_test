package com.gimadeev.zimad_test.presentaion.binding;

import com.gimadeev.zimad_test.data.model.DataPet;

public class PetConverter {

    public static BindingPet fromData(DataPet row) {
        return new BindingPet(row.getTitle(), row.getUrl());
    }

    public static DataPet toData(BindingPet row) {
        return new DataPet(row.getTitle(), row.getUrl());
    }
}
