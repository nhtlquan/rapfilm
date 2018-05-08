package com.example.rapfilm.network;

import com.example.rapfilm.Debug;
import com.example.rapfilm.application.FilmApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpRequestInterceptor;
import cz.msebera.android.httpclient.protocol.HttpContext;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmApiRequest {
    private static FilmApiInterface request;
    private static SearchApiInterface searchApiInterface;


    private FilmApiRequest() {
    }

    public static FilmApiInterface getInstance() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Debug.e("\n" + message)).setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addNetworkInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();
        if (request == null) {
            request = new Retrofit.Builder().baseUrl("http://api.aphim.co").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build().create(FilmApiInterface.class);
        }
        return request;
    }

    public static SearchApiInterface getSearchRequest() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Debug.e("\n" + message)).setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.addNetworkInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS).addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Connection", "close")
                                .addHeader("X-appota-key", "1-jkadnJ897H12nK127jNh-hkshydloHN19OkawslbU-cede9fe9b158b72783fd103dce08acd40dfe780c")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).build();
        if (searchApiInterface == null) {
            searchApiInterface = new Retrofit.Builder().baseUrl("https://search.appota.com").addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client).build().create(SearchApiInterface.class);
        }
        return searchApiInterface;
    }

}
