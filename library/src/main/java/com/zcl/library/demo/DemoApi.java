package com.zcl.library.demo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DemoApi {
    @POST("index.php")
    @FormUrlEncoded
    Observable<String> getAd(@Query("padapi") String padapi, @FieldMap Map<String, String> map);
}
