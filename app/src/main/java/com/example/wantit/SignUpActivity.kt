package com.example.wantit

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 툴바 생성코드
        val signUpToolbar = findViewById<Toolbar>(R.id.sign_up_toolbar)
        setSupportActionBar(signUpToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)    // 툴바 뒤로가기 버튼
        supportActionBar?.title = "회원가입"    // 툴바 제목

        // 회원가입 완료 버튼
        val signUpFinishButton = findViewById<Button>(R.id.sign_up_finish_button)
        signUpFinishButton.setOnClickListener {

            val signUpEmailtext = findViewById<EditText>(R.id.sign_up_emailtext)
            val signUpPassword = findViewById<EditText>(R.id.sign_up_password)

            // 1. 레트로핏 객체 생성
            val retrofit = Retrofit.Builder()
                .baseUrl("https://z.msporthome.store/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // 2. Service 객체 생성
            val apiservice : ApiService = retrofit.create(ApiService::class.java)

            // 3. call 객체 생성
            val callServer = apiservice.emailSignup(
                signUpEmailtext.text.toString(),
                signUpPassword.text.toString()
            )

            // 4. 네트워크 통신
            callServer.enqueue( object : Callback<ArrayList<BoardDataClass>> {
                override fun onResponse(
                    p0: Call<ArrayList<BoardDataClass>>,
                    p1: Response<ArrayList<BoardDataClass>>
                ) {
                    Log.e("회원가입 성공", "성공")    // 실패 메세지 로그
                }

                override fun onFailure(p0: Call<ArrayList<BoardDataClass>>, throwable: Throwable) {
                    Log.e("회원가입 실패", throwable.message.toString())    // 실패 메세지 로그
                }
            })

            // 현재 실패로 뜸 -> 하지만 데이터는 서버로 잘넘어감
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {  // 툴바 뒤로가기 버튼 눌렀을 때
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}