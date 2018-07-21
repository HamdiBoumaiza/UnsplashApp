package app.hb.unsplashwallpaper.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import app.hb.unsplashwallpaper.R;
import app.hb.unsplashwallpaper.adapters.PhotoAdapter;
import app.hb.unsplashwallpaper.databinding.ActivityMainBinding;
import app.hb.unsplashwallpaper.models.PhotoModel;
import app.hb.unsplashwallpaper.network.ApiService;
import app.hb.unsplashwallpaper.network.Client;
import app.hb.unsplashwallpaper.utils.Constants;
import app.hb.unsplashwallpaper.utils.UtilsHelper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PER_PAGE = "30";

    private ActivityMainBinding mainBinding;

    private CompositeDisposable mCompositeDisposable;

    private Context mContext;
    private ApiService mApiService;
    private PhotoAdapter photoAdapter;
    private ArrayList<PhotoModel> modelArrayList = new ArrayList<>();
    private int page = 1;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = getApplicationContext();
        progressDialog = new ProgressDialog(MainActivity.this);
        mCompositeDisposable = new CompositeDisposable();

        initRecycler();
        ProgressD();
        getPhotos(mContext.getString(R.string.latest), page);

        mainBinding.rbTabPopular.setOnClickListener(this);
        mainBinding.rbTabLatest.setOnClickListener(this);
        mainBinding.rbTabOldest.setOnClickListener(this);

    }


    private void initRecycler() {
        mainBinding.recyclerUnsplash.setLayoutManager(new GridLayoutManager(mContext, 2));
        photoAdapter = new PhotoAdapter(mContext, modelArrayList);
        mainBinding.recyclerUnsplash.setAdapter(photoAdapter);

        RecyclerScrollListener();
    }

    private void RecyclerScrollListener() {
        mainBinding.recyclerUnsplash.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    page += 1;
                    Timber.e("page number" + page);

                    ProgressD();

                    if (mainBinding.rbTabLatest.isChecked()) {
                        getPhotos(mContext.getString(R.string.latest), page);
                    } else if (mainBinding.rbTabOldest.isChecked()) {
                        getPhotos(mContext.getString(R.string.oldest), page);
                    } else {
                        getPhotos(mContext.getString(R.string.popular), page);
                    }
                }
            }
        });
    }


    // ws get list favorite
    private void getPhotos(String type, final int pageNumber) {
        if (!UtilsHelper.isInternetExist(mContext)) {
            Toast.makeText(mContext, mContext.getString(R.string.internet_does_not_exist), Toast.LENGTH_LONG).show();
        } else {
            mApiService = Client.getInstance(getApplicationContext()).getService();


            mCompositeDisposable.add(mApiService.getPhotos(
                    Constants.ACCESS_TOKEN
                    , PER_PAGE
                    , pageNumber
                    , type)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse,this::handleError));

        }
    }

    private void handleResponse(ArrayList<PhotoModel> photoModels) {
        if (photoModels != null) {
            if (page != 1) {
                photoAdapter.AddPhotoArray(photoModels);
            } else {
                photoAdapter.setPhotoModelArrayList(photoModels);
            }
            ProgressD();

        }


    }



    private void handleError(Throwable error) {
        Timber.e("on failure ws call: \n" + error.getMessage());
        ProgressD();
    }





    @Override
    public void onClick(View v) {
        ProgressD();
        page = 1 ;
        switch (v.getId()) {
            case R.id.rb_tab_popular:
                getPhotos(mContext.getString(R.string.popular), page);
                break;
            case R.id.rb_tab_latest:
                getPhotos(mContext.getString(R.string.latest), page);
                break;
            case R.id.rb_tab_oldest:
                getPhotos(mContext.getString(R.string.oldest), page);
                break;
        }

    }


    public void ProgressD() {
        if (!progressDialog.isShowing()) {
            progressDialog.setTitle(getString(R.string.please_wait));
            progressDialog.setMessage(getString(R.string.message_wait));
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
