package com.gimadeev.zimad_test.retrofit;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("api.php")
    Maybe<Response> getPets(@Query("query") String query);
}
