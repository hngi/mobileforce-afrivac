package Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("auth/signup")
    Call<ResponseBody> createUser(
            @Field("name")String name,
            @Field("country")String country,
            @Field("number(mobile number)")String number,
            @Field("password")String password,
            @Field("email")String email
    );
}
