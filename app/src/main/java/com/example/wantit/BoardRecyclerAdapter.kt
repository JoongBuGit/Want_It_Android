package com.example.wantit

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// 총 2개 클래스와 3개 메소드 선언 필수 -> 리사이클러뷰 사용조건
// Adapter Class와 필수 재선언 메소드 onCreateViewHolder,onBindViewHolder,getItemCount 만들기
// ViewHolder Class 만들기
class BoardRecyclerAdapter() : RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder>()  {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val textview : TextView = TODO()

        init {
            textView = view.findViewById(R.id.recycler_textView_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}