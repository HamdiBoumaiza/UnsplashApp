package app.hb.unsplashwallpaper.adapters;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import app.hb.unsplashwallpaper.R;
import app.hb.unsplashwallpaper.activities.DetailsPhotoActivity;
import app.hb.unsplashwallpaper.databinding.ItemPhotosBinding;
import app.hb.unsplashwallpaper.models.PhotoModel;
import app.hb.unsplashwallpaper.utils.Constants;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private ArrayList<PhotoModel> photoModelArrayList;
    private ItemPhotosBinding viewDataBinding;
    private Context mContext;
    private AdapterListener adapterListener;

    public PhotoAdapter(Context mContext, ArrayList<PhotoModel> mListoptions, AdapterListener adapterListener) {
        this.photoModelArrayList = mListoptions;
        this.mContext = mContext;
        this.adapterListener = adapterListener;

    }

    public ArrayList<PhotoModel> getPhotoModelArrayList() {
        return photoModelArrayList;
    }

    public void setPhotoModelArrayList(ArrayList<PhotoModel> photoModelArrayList) {
        this.photoModelArrayList = photoModelArrayList;
        notifyDataSetChanged();
    }

    public void AddPhotoArray(ArrayList<PhotoModel> photoModelArray) {
        if (photoModelArray != null) {
            photoModelArrayList.addAll(photoModelArray);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_photos, viewGroup, false);
        return new ViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.color.colorWhite)
                        .error(R.color.colorWhite)
                        .centerCrop())
                .load(photoModelArrayList.get(position).getUrls().getThumb())
                .into(viewDataBinding.imgPhotos);

    }


    @Override
    public int getItemCount() {
        return photoModelArrayList == null ? 0 : photoModelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public interface AdapterListener {
        void itemClickListener(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(ItemPhotosBinding itemView) {
            super(itemView.getRoot());
            this.setIsRecyclable(false);
            viewDataBinding.imgPhotos.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            adapterListener.itemClickListener(getAdapterPosition());
        }
    }


}