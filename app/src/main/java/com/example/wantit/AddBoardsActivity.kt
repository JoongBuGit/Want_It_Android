package com.example.wantit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

class AddBoardsActivity : AppCompatActivity() {

    lateinit var imageUri : Uri
    lateinit var bitmap: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_boards)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 수정하려고 액티비티에 접근 했을 때 코드
        val state = intent.getStringExtra("state")

        // xml에 있는 View들 findViewById로 연결
        val EditText_Title : EditText = findViewById<EditText>(R.id.EditText_Title)
        val EditText_Price : EditText = findViewById<EditText>(R.id.EditText_Price)
        val EditText_Description : EditText = findViewById<EditText>(R.id.EditText_Description)

        Log.e("수정액티비티 상태", state.toString())
        Log.e("수정액티비티 상태", intent.getStringExtra("title").toString())
        Log.e("수정액티비티 상태", intent.getStringExtra("imageName").toString())


        if (state.toString() == "edit") {
            // 넘겨받은 이미지 이름으로 이미지 받기
            val showImageImageview = findViewById<ImageView>(R.id.image_view_to_add)
            val imageName = intent.getStringExtra("imageName")
            Glide.with(this)
                .load("https://z.msporthome.store/images/$imageName")
                .into(showImageImageview)
            // 넘겨받은 텍스트 뷰에 넣기
            EditText_Title.setText(intent.getStringExtra("title"))
            EditText_Price.setText(intent.getIntExtra("price",0).toString())
            EditText_Description.setText(intent.getStringExtra("description"))
        }

        // 갤러리로 이동하는 이미지 버튼
        val imageButtonGoToCamera : ImageButton = findViewById(R.id.image_button_go_to_camera)
        imageButtonGoToCamera.setOnClickListener {

            // 사용자의 핸드폰의 버전을 확인하는 로그
            Log.i("안드로이드 정보, api : ", VERSION.SDK_INT.toString())
            Log.i("안드로이드 정보, version : ", VERSION.RELEASE)

            // 갤러리 권한이 없을 때
            if (
                ContextCompat.
                checkSelfPermission(this,Manifest.permission.READ_MEDIA_IMAGES)
             != PackageManager.PERMISSION_GRANTED ) {

                // 권한을 요구하고 갤러리로 이동
                this.requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 10)
//                Intent(MediaStore.ACTION_PICK_IMAGES)

                // 갤러리 권한이 있을 때
            } else {
                Log.i("권한", "권한 있음")

                // 갤러리로 가기위한 인텐트
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"

                // registerForActivityResult를 사용하여 액티비티 실행하기
                getActivityResult.launch(intent)
            }
        }

        // 서버에 게시글을 올리는 버튼
        val submitButtonMakeBoard : Button = findViewById(R.id.submit_button_make_board)
        submitButtonMakeBoard.setOnClickListener {

            // 서버에 넘겨줄 데이터
            val title = EditText_Title.text.toString()
            val price = EditText_Price.text.toString().toInt()
            val description = EditText_Description.text.toString()
            val boardId = intent.getStringExtra("boardId")

            // sharedPreferences에서 userId 데이터 가져오기
            val sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getString("userId", "")

            Log.e("EditText", "네임 : "+title)
            Log.e("EditText", "네임 : "+price)
            Log.e("EditText", "네임 : "+description)

            // 게시글 사진, 제목, 가격, 설명을 다 채우면 글작성, 아니면 못넘어감
            if (1 == 1) {

                val imageFile = File(bitmap) // bitmap으로 이미지 파일 만들기
                val filenameExtension =     // 파일확장자 변수
                    imageFile.name.substring(imageFile.name.indexOf(".")+1)

                // 파일 이미지를 담는 requestBody 변수
                val requestBody = MultipartBody.Part.createFormData(
                    "image",
                    imageFile.name,
                    RequestBody.create("image/$filenameExtension".toMediaTypeOrNull(), imageFile)
                )

                Log.e("이미지 네임", "네임 : "+imageFile.name)
                Log.e("이미지 네임", "네임 확장자 : +$filenameExtension")


                //1. retrofit 객체 생성
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://z.msporthome.store/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                //2. service 객체 생성
                val apiservice: ApiService = retrofit.create(ApiService::class.java)



                if (state.toString() == "edit") {     // 게시글 수정할 때
                    //3. Call 객체 생성
                    val callServer = apiservice.updateBoard(
                        "", title, price, description)

                    // 4. 네트워크 통신
                    callServer.enqueue(object : Callback<ArrayList<BoardDataClass>> {

                        override fun onResponse(call: Call<ArrayList<BoardDataClass>>, response: Response<ArrayList<BoardDataClass>>) {

                            Log.e("에러 성공", ""+response.message())

                        }

                        override fun onFailure(call: Call<ArrayList<BoardDataClass>>, throwable: Throwable) {
                            // 오류 발생 시
                            call.cancel()
                            Log.e("에러 실패", throwable.message.toString())
                            Log.e("에러 실패2", ""+imageUri.path.toString())

                        }

                    })

                } else {    // 새 게시글 만들 때
                    //3. Call 객체 생성
                    val callServer = apiservice.createBoard(
                        requestBody, title, price, description, imageFile.name, userId.toString())

                    // 4. 네트워크 통신
                    callServer.enqueue(object : Callback<ArrayList<BoardDataClass>> {

                        override fun onResponse(call: Call<ArrayList<BoardDataClass>>, response: Response<ArrayList<BoardDataClass>>) {

                            Log.e("에러 성공", ""+response.message())

                        }

                        override fun onFailure(call: Call<ArrayList<BoardDataClass>>, throwable: Throwable) {
                            // 오류 발생 시
                            call.cancel()
                            Log.e("에러 실패", throwable.message.toString())
                            Log.e("에러 실패2", ""+imageUri.path.toString())

                        }

                    })
                }





                finish()
            } else {
                val toast = Toast.makeText(this, "사진, 글, 가격을 모두 채워주세요", Toast.LENGTH_SHORT)
                toast.show()
            }

        }
    }

    // abstract class ActivityResultLauncher<I : Any?>
    // 추상화 클래스로 액티비티 결과를 제어하는 클래스 만들어주기
    private var getActivityResult
    = registerForActivityResult(ActivityResultContracts.StartActivityForResult() ) {

        // 받은 데이터가 널이 아닐 때 이미지 뷰에 이미지 보여주기
        if (it.data != null) {
            val imageViewToAdd : ImageView = findViewById(R.id.image_view_to_add)
            imageViewToAdd.setImageURI(it.data?.data)   // 이미지 경로를 설정해주기

            // 이미지 URI를 가져옴
            imageUri = it.data?.data ?: return@registerForActivityResult

            val fileName = imageUri.lastPathSegment ?: ""

            val mimeType: String? = it.data?.let {
                    imageUri1 ->
                contentResolver.getType(imageUri)
            }

            var fileExtension = ""

            if (mimeType == "image/jpeg") {
                fileExtension = "jpg"

            } else if (mimeType == "image/png") {
                fileExtension = "png"
            }

            Log.e("URI" , "URI : "+it.data?.data.toString())
            Log.e("URI" , "URI : "+imageUri.toString())
            Log.e("URI" , "확장자: "+mimeType)
            Log.e("URI" , "확장자2: "+fileExtension)


            // 이미지를 파일로 변환
         //   val bitmapImage : String = convertImageToFile(imageUri)
            bitmap = convertImageToFile(imageUri, fileExtension)
           // imageViewToAdd.setImageURI(bitmapImage.toUri()) // 만든 이미지 파일을 이미지뷰에 넣어주기

        }

    }

    // 파일 만드는 함수
    private fun convertImageToFile(imageUri: Uri, fileExtension: String): String {
        val contentResolver = applicationContext.contentResolver

        try {
            val inputStream = contentResolver.openInputStream(imageUri) ?: return ""

            val fileName2 = System.currentTimeMillis().toString()+".$fileExtension" // 고유한 파일 이름 생성


            val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName2)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            inputStream.close()
            outputStream.close()

            // 파일 생성 완료 처리
            Log.e("이미지 네임0", "네임 : "+fileName2)
            Toast.makeText(this, "이미지가 저장되었습니다: $fileName2", Toast.LENGTH_SHORT).show()

            return file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "이미지 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
            return ""
        }

    }

}