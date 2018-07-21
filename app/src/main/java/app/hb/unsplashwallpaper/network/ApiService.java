package app.hb.unsplashwallpaper.network;


import java.util.ArrayList;

import app.hb.unsplashwallpaper.models.PhotoModel;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiService {

    @GET("photos/")
    Observable<ArrayList<PhotoModel>> getPhotos(@Query("client_id") String access_key,
                                                @Query("per_page") String per_page,
                                                @Query("page") int page,
                                                @Query("order_by") String order_by
    );




}

