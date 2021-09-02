package br.com.herco.todoappmvp.services.database.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import br.com.herco.todoappmvp.application.TodoApp;
import br.com.herco.todoappmvp.constants.Constants;
import br.com.herco.todoappmvp.models.AuthUser;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {
    private static ApiClient instance;

    private static Retrofit retrofit;

    private ApiClient() {

        final String BASE_URL = "http://10.0.2.2:8080/";
//        final String BASE_URL = "http://demo0771206.mockable.io/";

        Gson gson = new GsonBuilder()
                .setDateFormat(Constants.Database.GSON_DATE_FORMAT)
                .create();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));


        OkHttpClient okHttpClient = getInterceptor();
        if (okHttpClient != null) {
            retrofitBuilder.client(okHttpClient);
        }

        retrofit = retrofitBuilder.build();
    }

    private static void initRetrofit() {
        //if (instance == null)
            instance = new ApiClient();
    }

    private OkHttpClient getInterceptor() {
        AuthUser authUser = TodoApp.getInstance().getAuthUser();

        if (authUser == null) {
            return null;
        } else {
            final String token = authUser.getToken();

            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("x-access-token", token).build();
                    return chain.proceed(request);
                }
            }).build();

            return okHttpClient;
        }
    }

    public static <T> T create(final Class<T> tClass) {
        initRetrofit();
        return retrofit.create(tClass);
    }
}
