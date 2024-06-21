package com.example.wantit

// 로그인 상태와 계정 정보를 담은 데이터 클래스
data class LoginDataClass(
    val loginState : String,
    val email : String,
    val userId : String
)
