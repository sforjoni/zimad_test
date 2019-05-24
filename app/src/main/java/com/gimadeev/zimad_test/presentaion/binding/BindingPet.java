package com.gimadeev.zimad_test.presentaion.binding;

import android.os.Parcel;
import android.os.Parcelable;

import com.gimadeev.zimad_test.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class BindingPet extends BaseObservable implements Parcelable {

    private String title;
    private String url;

    public BindingPet(String title, String url) {
        this.title = title;
        this.url = url;
    }

    protected BindingPet(Parcel in) {
        title = in.readString();
        url = in.readString();
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
        notifyPropertyChanged(BR.title);
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    public static final Creator<BindingPet> CREATOR = new Creator<BindingPet>() {
        @Override
        public BindingPet createFromParcel(Parcel in) {
            return new BindingPet(in);
        }

        @Override
        public BindingPet[] newArray(int size) {
            return new BindingPet[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(url);
    }
}
