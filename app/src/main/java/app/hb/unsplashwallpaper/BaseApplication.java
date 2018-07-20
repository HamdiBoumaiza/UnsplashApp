package app.hb.unsplashwallpaper;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by hamdi boumaiza on 10/06/2018.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());


    }


}
