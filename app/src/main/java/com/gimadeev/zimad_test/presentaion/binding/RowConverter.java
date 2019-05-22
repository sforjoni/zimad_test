package com.gimadeev.zimad_test.presentaion.binding;

import com.gimadeev.zimad_test.data.model.DataRow;

public class RowConverter {

    public static BindingRow fromData(DataRow row) {
        return new BindingRow(row.getTitle(), row.getUrl());
    }

    public static DataRow toData(BindingRow row) {
        return new DataRow(row.getText(), row.getImageUrl());
    }
}
