package com.zcl.library.demo;

import com.zcl.library.net.RetrofitUtils;
import com.zcl.library.net.UrlStrs;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;

public class DemoRequest {

    private static final String PADAPI = "coop-mobile-getAppStartAd.php";

    public static Observable<String> getAd() {
        Retrofit retrofit = RetrofitUtils.getAsyncRetrofit(RetrofitUtils.RetrofitConverter.STRING, UrlStrs.URL_MOBILE);
        DemoApi api = retrofit.create(DemoApi.class);
        HashMap<String, String> values = new HashMap<>();
        values.put("ch", "9339");
        values.put("pn", "cn.v6.sixrooms");
        values.put("av", "3.5");
        return api.getAd(PADAPI, values);
    }
}
