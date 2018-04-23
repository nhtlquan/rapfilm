package com.example.lequan.film.network;

import com.google.gson.JsonElement;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Callback;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SearchApiInterface {
    @GET("/apis/item.json")
    Observable<JsonElement> search(@Query("keyword") String str);
}
