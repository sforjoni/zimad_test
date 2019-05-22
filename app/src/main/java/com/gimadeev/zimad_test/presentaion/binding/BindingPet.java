package com.gimadeev.zimad_test.presentaion.binding;

import com.gimadeev.zimad_test.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class BindingPet extends BaseObservable {

    private String title;
    private String url;

    public BindingPet(String title, String url) {
        this.title = title;
        this.url = url;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.text);
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.imageUrl);
    }
}
