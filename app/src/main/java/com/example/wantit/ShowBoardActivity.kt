package com.example.wantit

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShowBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_board)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // toolbar를 액션바로 사용하기
        setSupportActionBar(findViewById(R.id.show_board_toolbar))

        // 이미지 받아와서 넣어주기
        val showImageImageview = findViewById<ImageView>(R.id.show_image_imageview)
        val imageName = intent.getStringExtra("imageName")
        Glide.with(this)
            .load("https://z.msporthome.store/images/$imageName")
            .into(showImageImageview)

        // 텍스트 받아서 넣어주기
        val showTitleTextview = findViewById<TextView>(R.id.show_title_textview)
        val showPriceTextview = findViewById<TextView>(R.id.show_price_textview)
        val showDescriptionTextview = findViewById<TextView>(R.id.show_description_textview)
        showTitleTextview.text = intent.getStringExtra("title")
        showPriceTextview.text = intent.getIntExtra("price", 0).toString()
        showDescriptionTextview.text = intent.getStringExtra("description")

        // 수정할 액티비티에 데이터 보내주기
        val intentEdit = Intent(this, ShowBoardActivity::class.java)
        intentEdit.putExtra("title", showTitleTextview.text)
        intentEdit.putExtra("price", intent.getIntExtra("price", 0))
        intentEdit.putExtra("description", showDescriptionTextview.text)
        intentEdit.putExtra("imageName", intent.getStringExtra("imageName"))



    }

    // toolbar 메뉴 적용하기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // 내가 쓴 게시글일 때만 수정 삭제할 수 있는 탭 만들어주기
        val boardUserId = intent.getStringExtra("userId").toString()
        val myUserId = getSharedPreferences("account", Context.MODE_PRIVATE)
            .getString("userId", "")

        if (boardUserId == myUserId) {
            menuInflater.inflate(R.menu.board_appbar_menu, menu)
        }
        return true
    }

    // 툴바 메뉴 선택 했을 때 실행
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_item -> {   // 툴바 수정 메뉴
                Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()


                // 수정 액티비티로 이동하기
                val state = "edit"
                val title = intent.getStringExtra("title")
                val price = intent.getIntExtra("price", 0)
                val description = intent.getStringExtra("description")
                val imageName = intent.getStringExtra("imageName")
                intent.getStringExtra("title")

                Log.e("액티비티 내부", price.toString())

                // 보낼 인탠트에 데이터 넣어주기
                val goEditIntent = Intent(this, AddBoardsActivity::class.java)
                goEditIntent.putExtra("state", state)
                goEditIntent.putExtra("title", title)
                goEditIntent.putExtra("price", price)
                goEditIntent.putExtra("description", description)
                goEditIntent.putExtra("imageName", imageName)
                getActivityResult.launch(goEditIntent)

            }
            R.id.delete_item -> {   // 툴바 삭제 메뉴

                // dialog 만들기
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("정말 삭제할까요?")
                dialogBuilder.setMessage("삭제 버튼을 누르면 게시글이 영구적으로 삭제됩니다")
                dialogBuilder.setNegativeButton("취소", null)
                dialogBuilder.setPositiveButton("삭제") {
                    dialoginterface: DialogInterface, i:Int -> Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show()

                    // 통신 하기
                    // 1. Retrofit 객체 생성 -> 서비스 객체를 만들기 위함
                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://z.msporthome.store/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    // 2. Service 객체 생성
                    val apiService = retrofit.create(ApiService::class.java)
                    // 3. call 객체 생성
                    val boardUserId = intent.getStringExtra("boardId").toString()    // 삭제할 게시글 ID
                    val callServer = apiService.deleteBoard(boardUserId)
                    // 4. 네트워크 통신
                    callServer.enqueue(object : Callback<String> {
                        override fun onResponse(p0: Call<String>, p1: Response<String>) {
                            Log.e("삭제 성공", "삭제 성공")
                        }
                        override fun onFailure(p0: Call<String>, p1: Throwable) {
                            Log.e("삭제 실패", p1.message.toString())
                        }
                    })

                    // 액티비티 종료
                    finish()
                }
                dialogBuilder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // abstract class ActivityResultLauncher<I : Any?>
    // 추상화 클래스로 액티비티 결과를 제어하는 클래스 만들어주기
    private val getActivityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }
}
