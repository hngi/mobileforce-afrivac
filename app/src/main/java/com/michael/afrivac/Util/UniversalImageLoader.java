package com.michael.afrivac.Util;

import android.content.Context;
import android.util.Log;

import com.michael.afrivac.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;


public class UniversalImageLoader {
    private static final String TAG = "UniversalImageLoader";
    private static final int defaultImage = R.drawable.pics;
    private Context mContext;

    public UniversalImageLoader(Context context) {
        this.mContext = context;
        Log.d(TAG, "UniversalImageLoader: started");
    }

    public ImageLoaderConfiguration getConfig(){
        Log.d(TAG, "getConfig: Returning image loader configuration");
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage) // resource or drawable
                .showImageForEmptyUri(defaultImage) // resource or drawable
                .showImageOnFail(defaultImage) // resource or drawable
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024)
                .build();


        return config;
    }


}
