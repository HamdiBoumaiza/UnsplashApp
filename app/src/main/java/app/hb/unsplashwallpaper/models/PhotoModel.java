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
    private URLS urls;

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

    public URLS getUrls() {
        return urls;
    }

    public class URLS implements Serializable{
        @SerializedName("raw")
        private String raw;

        @SerializedName("full")
        private String full;

        @SerializedName("regular")
        private String regular;

        @SerializedName("small")
        private String small;

        @SerializedName("thumb")
        private String thumb;

        public String getRaw() {
            return raw;
        }

        public String getFull() {
            return full;
        }

        public String getRegular() {
            return regular;
        }

        public String getSmall() {
            return small;
        }

        public String getThumb() {
            return thumb;
        }
    }
}
