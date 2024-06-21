package com.example.wantit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// 총 2개 클래스와 3개 메소드 선언 필수 -> 리사이클러뷰 사용조건
// Adapter Class와 필수 재선언 메소드 onCreateViewHolder,onBindViewHolder,getItemCount 만들기
// ViewHolder Class 만들기
class BoardRecyclerAdapter(private val dataSet: ArrayList<BoardDataClass>?, private val listener : OnItemClickListener) : RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder>()  {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val recyclerItemLayout : LinearLayout
        val imageViewBoard : ImageView
        val textViewTitle : TextView
        val textViewPrice : TextView

        init {
            recyclerItemLayout = view.findViewById(R.id.recycler_item_layout)
            imageViewBoard = view.findViewById(R.id.recycler_imageView)
            textViewTitle = view.findViewById(R.id.recycler_textView_title)
            textViewPrice = view.findViewById(R.id.recycler_textView_price)
        }
    }


    // Create new views (invoked by the layout manager)
      override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.board_recyclerview_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Glide 라이브러리로 URL이미지를 이미지뷰에 넣어주기
        val imageName = dataSet?.get(position)?.imageName.toString()
        Glide.with(viewHolder.itemView.context)
            .load("https://z.msporthome.store/images/$imageName")
            .into(viewHolder.imageViewBoard)

        viewHolder.textViewTitle.text = dataSet?.get(position)?.title
        viewHolder.textViewPrice.text = dataSet?.get(position)?.price.toString()+" 원"

        // 각 item에 리스너 등록
        viewHolder.itemView.setOnClickListener {

            // 프레그먼트에서 실행할 인터페이스 함수
            listener.onItemClick(position)
//            Log.e("터치 이벤트", "성공")

        }
    }

    // 리사이클러뷰 아이템 개수를 결정함
    override fun getItemCount() = dataSet?.size.toString().toInt()

    interface OnItemClickListener {
        fun onItemClick(position: Int) {

        }
    }

}