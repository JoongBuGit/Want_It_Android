package com.example.wantit

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.v2.all.BuildConfig

// Manifest에서 Application name 속성으로 실행되는 클래스
// 앱이 실행되면 가장먼저 이 클래스가 인스턴스화 된다
class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
//        KakaoSdk.init(this, BuildConfig)
        KakaoSdk.init(this, "dd55960c567af8cd9ece8031567a2c12")

    }

}