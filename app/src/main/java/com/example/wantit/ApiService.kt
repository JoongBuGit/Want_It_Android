package com.example.wantit

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // 게시글들을 가져오는 요청 예시
    @GET("https://z.msporthome.store/app")
    fun getDate(
       // @Path("boards") boards: String
    ):Call<ArrayList<DataClass>>


    // 게시글 생성
    @Multipart
    @POST("https://z.msporthome.store/CreateBoard")
    fun createBoard(
        @Part image: MultipartBody.Part,
        @Part("title") title : String,
        @Part("price") price : Int,
        @Part("description") description : String,
        @Part("imageName") imageName : String
    ): Call<ArrayList<DataClass>>

    // 게시글 읽기
    @GET("https://z.msporthome.store/ReadBoard") fun readBoard () : Call<ArrayList<DataClass>>

    // 게시글 수정
   // @PUT("https://z.msporthome.store/UpdateBoard") fun updateBoard() : Call<Post>


    // 게시글 삭제
   // @DELETE("https://z.msporthome.store/DeleteBoard") fun deleteBoard() : Call<Post>


}