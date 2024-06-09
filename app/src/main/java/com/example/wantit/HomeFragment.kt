package com.example.wantit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프레그먼트라서 view 변수를 이용해서 액티비티처럼 사용하기
        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        val boardRecyclerAdapter = BoardRecyclerAdapter()
//        val recyclerView: RecyclerView = view.findViewById(R.id.board_recyclerview)
//        recyclerView.adapter = boardRecyclerAdapter


        // 게시판을 추가하는 액티비티로 이동하는 버튼
        val buttonAddBoards: Button = view.findViewById(R.id.button_add_boards)
        buttonAddBoards.setOnClickListener {
            val intent = Intent(context, AddBoardsActivity::class.java)
            startActivity(intent)



        }

        return view
    }
}