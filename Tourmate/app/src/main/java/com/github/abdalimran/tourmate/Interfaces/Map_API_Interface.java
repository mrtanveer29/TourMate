package com.github.abdalimran.tourmate.Interfaces;

import com.github.abdalimran.tourmate.MapPojoModels.NearbyPlacesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Map_API_Interface {
    @GET("api/place/nearbysearch/json?&key=AIzaSyDQZoJKznf0uRkvJFqLYbZ7-1GeGudjmv0")
    Call<NearbyPlacesResponse> getNearbyPlaces(
            @Query("location") String locationString,
            @Query("rankby") String rankby,
//            @Query("radius") int rad,
            @Query("types") String type);
}