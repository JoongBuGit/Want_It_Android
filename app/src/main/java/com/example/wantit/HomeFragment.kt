package com.example.wantit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() , BoardRecyclerAdapter.OnItemClickListener{

    var arrayListBoardData : ArrayList<BoardDataClass>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 프레그먼트라서 view 변수를 이용해서 액티비티처럼 사용하기
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        // 서버에서 게시판 데이터 읽어오기
        //1. retrofit 객체 생성
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://z.msporthome.store/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        //2. service 객체 생성
        val apiservice: ApiService = retrofit.create(ApiService::class.java)
        //3. Call 객체 생성
        val callServer = apiservice.readBoard()

        // 4. 네트워크 통신
        callServer.enqueue(object : Callback<ArrayList<BoardDataClass>> {

            override fun onResponse(call: Call<ArrayList<BoardDataClass>>, response: Response<ArrayList<BoardDataClass>>) {
                // 서버에서 받은 게시판 데이터
                arrayListBoardData = response.body()

                Log.i("데이터", arrayListBoardData.toString())
                Log.i("데이터", arrayListBoardData?.size.toString())

                // 리사이클러뷰 코드
                // this@HomeFragment로 리스너 넣어주기
                val boardRecyclerAdapter = BoardRecyclerAdapter(arrayListBoardData, this@HomeFragment)
                val recyclerView: RecyclerView = view.findViewById(R.id.board_recyclerview)
                recyclerView.adapter = boardRecyclerAdapter

            }

            override fun onFailure(call: Call<ArrayList<BoardDataClass>>, throwable: Throwable) {
                // 오류 발생 시
                call.cancel()
                Log.e("에러", throwable.message.toString())
            }
            // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

        })

        // 게시판을 추가하는 액티비티로 이동하는 버튼
        val buttonAddBoards: Button = view.findViewById(R.id.button_add_boards)
        buttonAddBoards.setOnClickListener {
            val intent = Intent(context, AddBoardsActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    // 어뎁터에 있는 onItemClickListener를 implement 해서 override하기
    override fun onItemClick(position: Int) {
        super.onItemClick(position)

        // 액티비티에 넘겨줄 데이터 변수
        val title = arrayListBoardData?.get(position)?.title
        val price = arrayListBoardData?.get(position)?.price
        val description = arrayListBoardData?.get(position)?.description
        val imageName = arrayListBoardData?.get(position)?.imageName
        val userId = arrayListBoardData?.get(position)?.userId
        val boardId = arrayListBoardData?.get(position)?._id


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