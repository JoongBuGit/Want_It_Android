package com.example.wantit

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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


    }

    // toolbar 메뉴 적용하기
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.board_appbar_menu, menu)
        return true
    }

    // 툴바 메뉴 선택 했을 때 실행
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_item -> {
                Toast.makeText(this, "수정", Toast.LENGTH_SHORT).show()
            }
            R.id.delete_item -> {

                // dialog 만들기
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("정말 삭제할까요?")
                dialogBuilder.setMessage("삭제 버튼을 누르면 게시글이 영구적으로 삭제됩니다")
                dialogBuilder.setNegativeButton("취소", null)
                dialogBuilder.setPositiveButton("삭제") {
                    dialoginterface: DialogInterface, i:Int -> Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show()
                }
                dialogBuilder.show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

}
