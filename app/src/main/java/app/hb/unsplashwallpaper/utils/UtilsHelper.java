package app.hb.unsplashwallpaper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class UtilsHelper {

    // return true if internet cnx is availabe on devide
    public static boolean isInternetExist(Context context) {
        if (context == null)
            return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) && networkInfo.isConnected())
                return true;
        }
        return false;
    }


}
