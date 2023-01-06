package com.graphicless.createaccountwithroomdatabase.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.graphicless.createaccountwithroomdatabase.R
import com.graphicless.createaccountwithroomdatabase.model.Post
import com.graphicless.createaccountwithroomdatabase.model.User
import kotlinx.android.synthetic.main.list_item.view.*
import java.util.*

class PostAdapter(private var posts:List<Post>, private var user: User): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val currentUser = user

    class PostViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item:Post, user:User){

            val postMessage = item.post
            var showMessage = postMessage
            try{
                showMessage = postMessage.substring(0,100).plus("... read more")
            }catch (_: Exception)
            {

            }


            view.user_name.text = user.firstName.plus(" ").plus(user.lastName)
            view.connection_type.text = "You"
            view.title.text = user.title
            view.time.text = "16h"
            view.post.text = showMessage

            if(!Objects.equals(item.image, null)){
                try {
                    val imageBitmap = BitmapFactory.decodeByteArray(item.image, 0, item.image!!.size)
                    view.post_image.setImageBitmap(imageBitmap)
                }catch(exception: Exception){
                    Log.d("TAG", "exception: $exception")
                }
            }

            if(!Objects.equals(user.profilePic, null)){
                try {
                    val imageBitmap = BitmapFactory.decodeByteArray(user.profilePic, 0, user.profilePic!!.size)
                    view.user_image.setImageBitmap(imageBitmap)
                }catch(exception: Exception){
                    Log.d("TAG", "exception: $exception")
                }
            }

            view.post.setOnClickListener{
                view.post.text = postMessage
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return PostViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = posts[position]
        holder.bind(item, currentUser)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}