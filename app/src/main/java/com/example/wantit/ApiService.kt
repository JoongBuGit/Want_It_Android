package com.example.wantit

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    // 게시글 생성
    // 서버로 데이터는 잘 넘어가지만 막상 응답 값은 에러가 발생함
    @Multipart
    @POST("https://z.msporthome.store/CreateBoard")
    fun createBoard(
        @Part image: MultipartBody.Part,
        @Part("title") title : String,
        @Part("price") price : Int,
        @Part("description") description : String,
        @Part("imageName") imageName : String,
        @Part("userId") userId : String
    ): Call<ArrayList<BoardDataClass>>

    // 전체 게시글 읽기
    @GET("https://z.msporthome.store/ReadBoard") fun readBoard () : Call<ArrayList<BoardDataClass>>

    // 내 게시글 수정
    @PUT("https://z.msporthome.store/UpdateBoard") fun updateBoard(
        @Query("boardId") boardId: String,
        @Query("title") title: String,
        @Query("price") price: Int,
        @Query("description") description: String

    ) : Call<ArrayList<BoardDataClass>>


    // 내 게시글 삭제
    @DELETE("https://z.msporthome.store/DeleteBoard")
    fun deleteBoard(
        @Query("boardId") boardId: String
    ) : Call<String>

    // 내 게시글 읽기
    @GET("https://z.msporthome.store/ReadMyBoard")
    fun readMyBoard (
        @Query("userId") userId: String
    ): Call<ArrayList<BoardDataClass>>

    // 이메일 회원가입
    // 서버로 데이터는 잘 넘어가지만 막상 응답 값은 에러가 발생함
    @FormUrlEncoded
    @POST("https://z.msporthome.store/EmailSignUp")
    fun emailSignup (
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ArrayList<BoardDataClass>>

    // 이메일 로그인
    @FormUrlEncoded
    @POST("https://z.msporthome.store/EmailLogin")
    fun emailLogin (
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<ArrayList<LoginDataClass>>


}