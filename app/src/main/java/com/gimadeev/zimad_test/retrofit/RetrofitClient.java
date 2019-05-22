package com.gimadeev.zimad_test.retrofit;

import com.gimadeev.zimad_test.data.model.DataRow;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = new RetrofitClient();
    private static Api api = RetrofitClient.getInstance().getApi();

    private static final String BASE_URL = "http://kot3.com/xim/api.php";

    public static RetrofitClient getInstance() {
        return instance;
    }

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

    public Flowable<List<DataRow>> loadPets(String type) {
        return api.getPets(type).map(new Function<Response, List<DataRow>>() {
            @Override
            public List<DataRow> apply(Response response) throws Exception {
                return response.data;
            }
        });
    }
}
