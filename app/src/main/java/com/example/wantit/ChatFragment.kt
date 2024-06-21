package com.example.wantit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val chatWebView = view?.findViewById<WebView>(R.id.chat_webview)

        chatWebView?.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }


//        chatWebView.webViewClient = WebViewClient()

//        chatWebView?.loadUrl("http://i.msporthome.store:3001/chat")

        chatWebView?.loadUrl("https://z.msporthome.store/chat")

//        chatWebView?.loadUrl("https://www.naver.com")





        return view
    }
}