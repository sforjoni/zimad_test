package com.gimadeev.zimad_test.presentaion.binding;

import com.gimadeev.zimad_test.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class BindingRow extends BaseObservable {

    private String text;
    private String imageUrl;

    public BindingRow(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    @Bindable
    public String getText() {
        return text;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }
}
