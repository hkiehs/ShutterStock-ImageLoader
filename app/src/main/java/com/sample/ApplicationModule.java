package com.sample;

import android.content.Context;
import android.util.Base64;

import com.sample.adapter.RecyclerViewAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.sql.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

@Module
public class ApplicationModule {

    private static final String BASE_URL = "https://api.shutterstock.com";
    private static final String CLIENT_ID = "335e17752f994dddfb1e";
    private static final String CLIENT_KEY = "ea951dc0e3216c67f7391c8518b4a46b28a8d903";

    private final App app;

    public ApplicationModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.app;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        File httpCacheDirectory = new File(app.getCacheDir(), "responses");

        Cache cache = null;
        cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        OkHttpClient okHttpClient = new OkHttpClient();
        if (cache != null) {
            okHttpClient.setCache(cache);
        }

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(okHttpClient))
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        final String credentials = CLIENT_ID + ":" + CLIENT_KEY;
                        // create Base64 encoded string
                        String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", string);
                    }
                }).build();

        return restAdapter;
    }

    @Provides
    @Singleton
    RecyclerViewAdapter provideRecyclerViewAdapter() {
        return new RecyclerViewAdapter();
    }
}
