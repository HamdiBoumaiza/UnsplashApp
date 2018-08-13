package app.hb.unsplashwallpaper.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hamdi boumaiza on 11/06/2018.
 */
public class PhotoModel implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("color")
    private String color;

    @SerializedName("description")
    private String description;

    @SerializedName("urls")
    private Urls urls;

    public PhotoModel() {
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public Urls getUrls() {
        return urls;
    }


}
