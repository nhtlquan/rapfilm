package com.example.rapfilm.network;

import com.google.gson.JsonElement;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Callback;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FilmApiInterface {
    @FormUrlEncoded
    @POST("/comment/add")
    Observable<JsonElement> addComment(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/addpackagefree")
    void addPackageFree(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/content/advertise")
    Observable<JsonElement> advertise(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/analytic_ads")
    Observable<JsonElement> analyticAds(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/package/buyPackage")
    Observable<JsonElement> buyPackage(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/account/change_password")
    Observable<JsonElement> changePass(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/account/check_logout")
    Observable<JsonElement> checkOut(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/user/check_phone_number")
    Observable<JsonElement> checkPhoneNumber(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/user/check_phone_number")
    Observable<JsonElement> checkPhoneNumberForgotPass(@Query("lang") String str, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/user/check_vip")
    Observable<JsonElement> checkVip(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/collection")
    Observable<JsonElement> collection(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/f/content/comment")
    Observable<JsonElement> comment(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/client/ads_config")
    Observable<JsonElement> configAds(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/v2/user/decrVip")
    Observable<JsonElement> decrVip(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/v2/comment/deleteComment")
    Observable<JsonElement> deleteCommentManager(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/deleterecents")
    void deleteRecents(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/content/detail")
    Observable<JsonElement> detailFilm(@Query("access_token") String str, @Query("lang") String str2, @Query("build") int i, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/discover")
    Observable<JsonElement> discover(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/download")
    Observable<JsonElement> download(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/get_list_episode")
    Observable<JsonElement> episodes(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/user/feedback")
    Observable<JsonElement> feedback(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/v2/tv/genCode")
    Observable<JsonElement> genCode(@Query("lang") String str, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/genres")
    Observable<JsonElement> genres(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/ads/getCampaign")
    Observable<JsonElement> getCampaign(@Query("access_token") String str, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/comment/get_detail")
    Observable<JsonElement> getCommentDetails(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/getcomments")
    Observable<JsonElement> getComments(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/comment/getComments")
    Observable<JsonElement> getListComment(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/v2/comment/getAllComments")
    Observable<JsonElement> getListCommentManager(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/favourite")
    Observable<JsonElement> getListFavourite(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/notification/get_list_film")
    Observable<JsonElement> getListFilmNotification(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/package/getPackages")
    Observable<JsonElement> getPackage(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/getPackages")
    void getPackages(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/v2/package/getPackages")
    Observable<JsonElement> getPackagesVer2(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/client/getSurvey")
    Observable<JsonElement> getSurvey(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/transaction/getTransactions")
    Observable<JsonElement> getTransitionHistory(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/user/get_info")
    Observable<JsonElement> getUserInfo(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/getuserpackage")
    void getUserPackageInfo(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/content/recent")
    Observable<JsonElement> getUserRecent(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/w/content/top_newest")
    Observable<JsonElement> getWallpaper(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/historytransaction")
    void historyTransaction(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/client/init")
    Observable<JsonElement> init(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/comment/like")
    Observable<JsonElement> likeComment(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/likecomment")
    void likeComment(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/account/login_new")
    Observable<JsonElement> login(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/account/login_facebook_new")
    Observable<JsonElement> loginFacebook(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/account/login_google_new")
    Observable<JsonElement> loginGoogle(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/account/logout")
    Observable<JsonElement> logout(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/client/maintain")
    Observable<JsonElement> maintain(@Query("lang") String str, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/top_newest")
    Observable<JsonElement> newestCategory(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/v1/services/ibanking")
    Observable<JsonElement> paymentBank(@Query("api_key") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/v1/services/card_charging")
    Observable<JsonElement> paymentCard(@Query("api_key") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/rate")
    Observable<JsonElement> rate(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/recentdetail")
    Observable<JsonElement> recentDetail(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/account/register_user")
    Observable<JsonElement> registerUser(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/v2/comment/replyComment")
    Observable<JsonElement> replyComment(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/report_error")
    void reportError(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @GET("/content/genres")
    Observable<JsonElement> requestFacebook(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/account/reset_password_native")
    Observable<JsonElement> resetPass(@Query("lang") String str, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/search")
    Observable<JsonElement> search(@Query("lang") String str, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/ads/analytic_ads")
    Observable<JsonElement> sendImpression(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/user/gift_code")
    Observable<JsonElement> sendPromocode(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/notification/add_user_film")
    Observable<JsonElement> subscribe(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/ads/textAds")
    Observable<JsonElement> textAds(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/top_download")
    Observable<JsonElement> topDownload(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/top_imdb")
    Observable<JsonElement> topIMDb(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/top_vote")
    Observable<JsonElement> topVote(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/processtransaction")
    void transaction(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/notification/delete_user_film")
    Observable<JsonElement> unsubscribe(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/user/update_info")
    Observable<JsonElement> updateUserInfo(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("/content/user_recent")
    void userRecent(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/content/user_setting")
    void userSetting(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map, Callback<JsonElement> callback);

    @FormUrlEncoded
    @POST("/user/invite_code")
    Observable<JsonElement> verifyInvite(@Query("access_token") String str, @Query("lang") String str2, @FieldMap Map<String, String> map);
}
