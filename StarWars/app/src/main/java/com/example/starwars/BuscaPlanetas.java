package com.example.starwars;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BuscaPlanetas extends AsyncTaskLoader<String> {
    private String mQueryString;
    BuscaPlanetas(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
        Log.d("uraa4",mQueryString);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground()  {

        return Conecta.procuraInfoPlanetas(mQueryString);
    }
}

