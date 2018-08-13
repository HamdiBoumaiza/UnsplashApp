package app.hb.unsplashwallpaper.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import app.hb.unsplashwallpaper.BuildConfig;
import app.hb.unsplashwallpaper.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Client {
    private static Client instance = null;
    private Retrofit mRetrofitClient;
    private OkHttpClient mOkHttpClient;

    private ApiService mService;

    private Client(final Context context) {
        //   mContext = context;
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(30, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        mOkHttpClient = okHttpBuilder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofitClient = new Retrofit.Builder().baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient)
                .build();

        mService = mRetrofitClient.create(ApiService.class);
    }

    public static Client getInstance(Context context) {
        if (instance == null) {
            instance = new Client(context);
        }
        return instance;
    }

    public ApiService getService() {
        return mService;
    }

}