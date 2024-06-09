package com.example.wantit

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

lateinit var textView : TextView
class HttpTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_http_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


//        textView = findViewById(R.id.textview)
//
//        val button : Button = findViewById(R.id.Button)
//        button.setOnClickListener {
//            apiRequest()
//        }
    }


    // 네트워크 통신하는 함수
//    private fun apiRequest() {
//        //1. retrofit 객체 생성
//        val retrofit : Retrofit = Retrofit.Builder()
//            .baseUrl("https://z.msporthome.store/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        //2. service 객체 생성
//        val apiservice: ApiService = retrofit.create(ApiService::class.java)
//
//        //3. Call 객체 생성
//        val callServer = apiservice.readBoard()
//
//        // 4. 네트워크 통신
//        callServer.enqueue(object : Callback<ArrayList<DataClass>> {
//
//            override fun onResponse(call: Call<ArrayList<DataClass>>, response: Response<ArrayList<DataClass>>) {
//
//                // boardsData?.price 형식이 맞는지 확인하세요. 실제 DataClass 구조에 맞게 수정해야 합니다.
//                textView.append("\nHTTPS 상태 : ${response.message()} \n\n")
//                textView.append("서버 DB데이터\n"+response.body().toString())
//
//            }
//
//            override fun onFailure(call: Call<ArrayList<DataClass>>, throwable: Throwable) {
//                // 오류 발생 시
//                call.cancel()
//                textView.append(throwable.message)
//                Log.e("에러", throwable.message.toString())
//            }
//
//        })
//
//    }

}