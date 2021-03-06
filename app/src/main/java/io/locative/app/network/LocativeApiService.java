package io.locative.app.network;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by mkida on 16/08/2014.
 */

public interface LocativeApiService {

    @GET("/api/session")
    void login(
            @Query("username") String username,
            @Query("password") String password,
            @Query("origin") String origin,
            Callback<String> callback);

    @FormUrlEncoded
    @POST("/api/signup")
    void signup(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email,
            @Field("token") String token,
            Callback<String> callback);

    @GET("/api/session/{session}")
    void checkSession(
            @Path("session") String sessionId,
            Callback<String> callback);

    @FormUrlEncoded
    @POST("/api/fencelogs/{session}")
    void dispatchFencelog(
            @Path("session") String sessionId,
            @Field("longitude") float longitude,
            @Field("latitude") float latitude,
            @Field("locationId") String locationId,
            @Field("httpUrl") String httpUrl,
            @Field("httpMethod") String httpMethod,
            @Field("httpResponseCode") String httpResponseCode,
            @Field("httpResponse") String httpResponse,
            @Field("eventType") String eventType,
            @Field("fenceType") String fenceType,
            @Field("origin") String origin,
            Callback<String> callback);

    @GET("/api/geofences")
    void getGeofences(
            @Query("sessionId") String sessionId,
            Callback<String> callback);

    @GET("/api/fencelogs/{session}")
    void getFencelogs(
            @Path("session") String sessionId,
            Callback<String> callback);
}
