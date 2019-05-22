package com.gimadeev.zimad_test.presentaion.binding;

import com.gimadeev.zimad_test.data.model.DataRow;

public class PetConverter {

    public static BindingPet fromData(DataRow row) {
        return new BindingPet(row.getTitle(), row.getUrl());
    }

    public static DataRow toData(BindingPet row) {
        return new DataRow(row.getTitle(), row.getUrl());
    }
}
