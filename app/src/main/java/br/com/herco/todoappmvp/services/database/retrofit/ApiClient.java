package br.com.herco.todoappmvp.services.database.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.herco.todoappmvp.constants.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    private static ApiClient instance;

    private static Retrofit retrofit;

    private ApiClient() {

        final String BASE_URL = "http://10.0.2.2:3000/";
//        final String BASE_URL = "http://demo0771206.mockable.io/";

        Gson gson = new GsonBuilder()
                .setDateFormat(Constants.Database.GSON_DATE_FORMAT)
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private static void initRetrofit() {
        if (instance == null) instance = new ApiClient();
    }

    public static <T> T create(final Class<T> tClass) {
        initRetrofit();
        return retrofit.create(tClass);
    }
}
