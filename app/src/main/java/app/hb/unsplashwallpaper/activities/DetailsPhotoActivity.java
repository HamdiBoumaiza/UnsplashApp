package app.hb.unsplashwallpaper.activities;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;

import app.hb.unsplashwallpaper.R;
import app.hb.unsplashwallpaper.databinding.ActivityDetailsPhotosBinding;
import app.hb.unsplashwallpaper.models.PhotoModel;
import app.hb.unsplashwallpaper.utils.Constants;


public class DetailsPhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityDetailsPhotosBinding detailsPhotosBinding;
    private PhotoModel photoModel;
    private Context mContext ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsPhotosBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_photos);

        mContext=  getApplicationContext();
        detailsPhotosBinding.fabChangeWallpaper.setOnClickListener(this);


        photoModel = (PhotoModel) getIntent().getSerializableExtra(Constants.PARAM_PHOTOS);

        if (photoModel != null) {
            Glide.with(getApplicationContext())
                    .applyDefaultRequestOptions(new RequestOptions()
                            .placeholder(R.color.colorGrey)
                            .error(R.color.colorGrey)
                            .centerCrop())
                    .load(photoModel.getUrls().getRegular())
                    .into(detailsPhotosBinding.imgFullImage);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_change_wallpaper:
                SetWallpaperAlertConfirm();
                break;
        }
    }


    // changing the phone wallpaper
    private void SetWallpaper() {
        detailsPhotosBinding.imgFullImage.buildDrawingCache();
        Bitmap bitmap = detailsPhotosBinding.imgFullImage.getDrawingCache();

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, mContext.getString(R.string.success_message), Toast.LENGTH_SHORT).show();
    }


    // alert for confirming changing the phone wallpaper
    private void SetWallpaperAlertConfirm() {
        AlertDialog.Builder altdial = new AlertDialog.Builder(this);
        altdial.setMessage(mContext.getString(R.string.change_message)).setCancelable(false)
                .setPositiveButton(mContext.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SetWallpaper();
                    }
                })
                .setNegativeButton(mContext.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = altdial.create();
        alert.setTitle(mContext.getString(R.string.change));
        alert.show();
    }


}
