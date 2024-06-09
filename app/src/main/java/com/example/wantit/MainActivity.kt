package com.example.wantit

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        val post_item1: TextView = findViewById(R.id.home_fragment_textview)
//        val post_item1 = findViewById<TextView>(R.id.bottom_nav)




        // 바텀네비게이션을 프래그먼트와 연결시키는 코드
        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavi.setOnItemSelectedListener {item ->
            when (item.itemId) {
                R.id.bottom_navigation_home -> {
                    val home = supportFragmentManager.beginTransaction()
                    val homeFragment = HomeFragment()
                    home.replace(R.id.fragment_home, homeFragment)

                    home.commit()


                    true
                }
                R.id.bottom_navigation_chat -> { val home = supportFragmentManager.beginTransaction()
                    val chatFragment = ChatFragment()
                    home.replace(R.id.fragment_home, chatFragment)

                    home.commit()
                    true}
                R.id.bottom_navigation_mypage -> { val home = supportFragmentManager.beginTransaction()
                    val mypageFragment = MypageFragment()
                    home.replace(R.id.fragment_home, mypageFragment)

                    home.commit()
                    true}


                else -> false
            }
        }



//        // 바텀네비게이션 밑에 여백공간이 생기는데 이를 없애 주는 코드
//        val bottomNavi = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavi.setOnApplyWindowInsetsListener(null)


        // 임시로 아이템을 보여주는 코드를 만듬 -> 후에 수정해야함
//        val post_item1 = findViewById<LinearLayout>(R.id.test_item1)
//        post_item1.setOnClickListener {
////            val intent = Intent(this, PostItemActivity::class.java)
////            startActivity(intent)
//        }

//        setContentView(R.id.home_fragment_textview)
//        val post_item1: TextView = findViewById(R.id.home_fragment_textview)


    }
}