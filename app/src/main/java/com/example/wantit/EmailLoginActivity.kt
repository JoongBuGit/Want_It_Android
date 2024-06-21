package com.example.wantit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

class EmailLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_email_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 툴바 생성
        val emailLoginToolbar = findViewById<Toolbar>(R.id.email_login_toolbar)
        setSupportActionBar(emailLoginToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
        supportActionBar?.title = "이메일로 로그인"          // 툴바 제목

        // 로그인 완료 버튼
        val emailLoginFinishButton = findViewById<Button>(R.id.email_login_finish_button)
        emailLoginFinishButton.setOnClickListener {
            val loginEmialtext = findViewById<EditText>(R.id.login_emailtext)
            val loginPassword = findViewById<EditText>(R.id.login_password)

            // 1. 레트로핏 객체 생성
            val retrofit = Retrofit.Builder()
                .baseUrl("https://z.msporthome.store/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            // 2. Service 객체 생성
            val apiservice : ApiService = retrofit.create(ApiService::class.java)

            // 3. call 객체 생성
            val callServer = apiservice.emailLogin(
                loginEmialtext.text.toString(),
                loginPassword.text.toString()
            )

            // 로그인 상태 변수
            var loginState = "false"
            // 4. 네트워크 통신
            callServer.enqueue( object : Callback<ArrayList<LoginDataClass>> {
                override fun onResponse(
                    p0: Call<ArrayList<LoginDataClass>>,
                    response: Response<ArrayList<LoginDataClass>>
                ) {
                    loginState  = response.body()?.get(0)?.loginState.toString()
                    val email = response.body()?.get(0)?.email.toString()
                    val userId = response.body()?.get(0)?.userId.toString()

                    // 로그인 성공
                    if (loginState == "true") {
                        Toast.makeText(this@EmailLoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()

                        // 회원가입 액티비티로 이동
                        val intent = Intent(this@EmailLoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 로그인 할때 전 액티비티를 다 지우는 플레그
                        startActivity(intent)

                        // LoginDataClass 데이터를 sharedpreferences에 저장하기 -> 다른 액티비티에서도 사용하기 위해서
                        val sharedpreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
                        sharedpreferences.edit()
                            .putString("loginState", loginState)
                            .putString("email", email)
                            .putString("userId", userId)
                            .apply()


                    } else {
                    // 로그인 실패
                        Toast.makeText(this@EmailLoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("로그인 통신 성공", "성공")    // 실패 메세지 로그
                    Log.e("로그인 통신 데이터", "데이터 : "+response.body())    // 실패 메세지 로그


                }

                override fun onFailure(p0: Call<ArrayList<LoginDataClass>>, throwable: Throwable) {
                    Log.e("로그인 통신 실패", throwable.message.toString())    // 실패 메세지 로그

                }
            })

        }

        // 회원가입 액티비티로 이동
        val signupTextview = findViewById<TextView>(R.id.textview_signup)
        signupTextview.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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