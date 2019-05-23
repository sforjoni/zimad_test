package com.gimadeev.zimad_test.retrofit;

import com.gimadeev.zimad_test.data.model.DataPet;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = new RetrofitClient();
    private static Api api = RetrofitClient.getInstance().getApi();

    private static final String BASE_URL = "http://kot3.com/xim/";

    public static RetrofitClient getInstance() {
        return instance;
    }

    private RetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }

    public Maybe<List<DataPet>> loadPets(String type) {
        return api.getPets(type).map(new Function<Response, List<DataPet>>() {
            @Override
            public List<DataPet> apply(Response response) throws Exception {
                return response.data;
            }
        });
    }
}
