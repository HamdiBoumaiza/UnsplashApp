package app.hb.unsplashwallpaper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import app.hb.unsplashwallpaper.R;


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
        Toast.makeText(context, context.getString(R.string.internet_does_not_exist), Toast.LENGTH_LONG).show();
        return false;
    }
}
