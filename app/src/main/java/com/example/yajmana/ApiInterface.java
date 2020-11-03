package com.example.yajmana;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    // API's endpoints

    @FormUrlEncoded // annotation used in POST type requests
    //Verify Login
    @POST("/yajmana/login.php") Call<LoginResponse> checkLogin( @Field("username") String username, @Field("password") String password );
    //@POST("/yajmana/logout.php") Call<LoginResponse> logout();

    //Vanshawal
    @FormUrlEncoded
    @POST("/yajmana/vanshawal/create.php") Call<SignUpResponse> createVanshawal( @Field("late_fullname") String late_fullname, @Field("city") String city, @Field("taluka") String taluka, @Field("district") String district, @Field("caste") String caste, @Field("death_anniversary") String death_anniversary, @Field("relation") String relation, @Field("yajman") String yajman, @Field("father") String father, @Field("mother") String mother, @Field("grandpa") String grandpa, @Field("panjoba") String panjoba, @Field("nipanjoba") String nipanjoba, @Field("real_uncle") String real_uncle, @Field("cousin_uncle") String cousin_uncle, @Field("real_uncle_son") String real_uncle_son, @Field("cousin_uncle_son") String cousin_uncle_son, @Field("real_brother") String real_brother, @Field("real_brother_son") String real_brother_son, @Field("son") String son, @Field("mobileno") String mobileno, @Field("gender") String gender, @Field("tip") String tip, @Field("signatureImagePath") String serverImagePath);

    @FormUrlEncoded
    @POST("/yajmana/vanshawal/update.php") Call<SignUpResponse> updateYajamana(@Field("late_fullname") String late_fullname, @Field("city") String city, @Field("taluka") String taluka, @Field("district") String district, @Field("caste") String caste, @Field("death_anniversary") String death_anniversary, @Field("relation") String relation, @Field("yajman") String yajman, @Field("father") String father, @Field("mother") String mother, @Field("grandpa") String grandpa, @Field("panjoba") String panjoba, @Field("nipanjoba") String nipanjoba, @Field("real_uncle") String real_uncle, @Field("cousin_uncle") String cousin_uncle, @Field("real_uncle_son") String real_uncle_son, @Field("cousin_uncle_son") String cousin_uncle_son, @Field("real_brother") String real_brother, @Field("real_brother_son") String real_brother_son, @Field("son") String son, @Field("mobileno") String mobileno, @Field("tip") String tip, @Field("vanshawal_no") String vanshawal_no);

    //Upload Signature Image
    @Multipart
    @POST("/yajmana/vanshawal/upload.php") Call<SignUpResponse> uploadSignature(@Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("/yajmana/vanshawal/search.php") Call<List<Vanshawal>> searchVanshawal( @Field("late_firstname") String late_firstname, @Field("late_middlename") String late_middlename, @Field("late_lastname") String late_lastname, @Field("caste") String caste, @Field("city") String city, @Field("taluka") String taluka, @Field("district") String district, @Field("death_anniversary") String death_anniversary, @Field("mobile") String mobile );

    @Headers("Cache-Control:no-cache")
    @GET("/yajmana/vanshawal/read.php") Call<List<Vanshawal>> getVanshawal();

    @FormUrlEncoded
    @POST("/yajmana/vanshawal/detail.php") Call<List<Vanshawal>> getVanshawalDetails( @Field("vanshawal_no") String vanshawal_no );

    //Profile
    @Headers("Cache-Control:no-cache")
    @GET("/yajmana/profile/read.php") Call<List<Profile>> getProfile();

    @FormUrlEncoded
    @POST("yajmana/profile/update.php") Call<LoginResponse> updateProfile( @Field("first_name") String first_name, @Field("middle_name") String middle_name, @Field("last_name") String last_name, @Field("mobile") String mobile_no, @Field("email") String email );

    //Feedback
    @Headers("Cache-Control:no-cache")
    @GET("yajmana/feedback/read.php") Call<List<Feedback>> getFeedback();

    @FormUrlEncoded
    @POST("yajmana/feedback/add.php") Call<LoginResponse> addFeedback(@Field("full_name") String full_name, @Field("vanshawal_code") String vanshawalCode, @Field("content") String content);

    @FormUrlEncoded
    @Headers("Cache-Control:no-cache")
    @POST("yajmana/vanshawal/search.php") Call<List<Vanshawal>> searchByVanshawalCode(@Field("vanshawal_code") String vanshawalCode);
}
