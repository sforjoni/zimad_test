package com.gimadeev.zimad_test.presentaion.binding;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("app:imageUrl")
    public static void loadImage(ImageView view, String url) {
        if (!url.isEmpty()) {
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerInside()
                    .into(view);
        }
    }
}
