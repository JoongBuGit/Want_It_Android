package com.example.wantit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MypageFragment: Fragment() , BoardRecyclerAdapter.OnItemClickListener{

    var arrayListMyBoardData: ArrayList<BoardDataClass>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_mypage, container, false)


        // sharedPreferences에서 계정 정보 가져오기
        val sharedPreferences = activity?.getSharedPreferences("account", Context.MODE_PRIVATE)
        val nickname = sharedPreferences?.getString("email", "")
        val userId = sharedPreferences?.getString("userId", "")

        // 유저 이메일을 닉네임 텍스트로 보여주기
        val nicknameTextview = view?.findViewById<TextView>(R.id.nickname_textview)
        nicknameTextview?.text = nickname

        // 내 게시글 버튼 -> 누르면 내 게시글들을 리사이클러뷰에 담아서 보여준다
        val showMyBoardButton = view.findViewById<Button>(R.id.show_my_board_button)
        showMyBoardButton.setOnClickListener {


            // 서버에서 게시판 데이터 읽어오기
            //1. retrofit 객체 생성
            val retrofit : Retrofit = Retrofit.Builder()
                .baseUrl("https://z.msporthome.store/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            //2. service 객체 생성
            val apiservice: ApiService = retrofit.create(ApiService::class.java)
            //3. Call 객체 생성
            val callServer = apiservice.readMyBoard(userId.toString())

            // 4. 네트워크 통신
            callServer.enqueue(object : Callback<ArrayList<BoardDataClass>> {

                override fun onResponse(call: Call<ArrayList<BoardDataClass>>, response: Response<ArrayList<BoardDataClass>>) {
                    // 서버에서 받은 게시판 데이터
                    arrayListMyBoardData = response.body()

                    Log.i("데이터", arrayListMyBoardData.toString())
                    Log.i("데이터", arrayListMyBoardData?.size.toString())

                    // 리사이클러뷰 코드
                    val boardRecyclerAdapter = BoardRecyclerAdapter(arrayListMyBoardData, this@MypageFragment)
                    val recyclerView: RecyclerView = view.findViewById(R.id.my_board_recyclerview)
                    recyclerView.adapter = boardRecyclerAdapter

                }

                override fun onFailure(p0: Call<ArrayList<BoardDataClass>>, p1: Throwable) {
                }
            })
        }


        return view
    }

    override fun onItemClick(position: Int) {
        super.onItemClick(position)

        // 액티비티에 넘겨줄 데이터 변수
        val title = arrayListMyBoardData?.get(position)?.title
        val price = arrayListMyBoardData?.get(position)?.price
        val description = arrayListMyBoardData?.get(position)?.description
        val imageName = arrayListMyBoardData?.get(position)?.imageName
        val userId = arrayListMyBoardData?.get(position)?.userId
        val boardId = arrayListMyBoardData?.get(position)?._id

        // 게시글을 보여주는 액티비티로 데이터를 넣어서 이동
        val intent = Intent(context, ShowBoardActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("price", price)
        intent.putExtra("description", description)
        intent.putExtra("imageName", imageName)
        intent.putExtra("userId", userId)
        intent.putExtra("boardId", boardId)

        startActivity(intent)

    }
}