package com.graphicless.createaccountwithroomdatabase.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.graphicless.basiclinkedin.SharedPreference
import com.graphicless.createaccountwithroomdatabase.databinding.FragmentCreatePostBinding
import com.graphicless.createaccountwithroomdatabase.model.Post
import com.graphicless.createaccountwithroomdatabase.model.User
import com.graphicless.createaccountwithroomdatabase.viewmodel.UserViewModel
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


class CreatePostFragment : Fragment() {
    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreference = SharedPreference(requireContext())
        val userName = sharedPreference.getValueString("userName")!!

        binding.btnPost.setOnClickListener {
            val postMessage = binding.etPost.text.toString()

            val post: Post = if (binding.ivPostImage.isVisible) {
                val bitmap = binding.ivPostImage.drawable.toBitmap()
                val imageByteArray = getBitmapAsByteArray(bitmap)
                Post(0, userName, postMessage, imageByteArray!!)
            } else {
                Post(0, userName, postMessage, null)
            }

            viewModel.addPost(post)

            val action =
                CreatePostFragmentDirections.actionCreatePostFragmentToProfileFragment()
            findNavController().navigate(action)

//            var userList: List<User>? = null
//            viewModel.allUser.observe(viewLifecycleOwner) {
//                userList = it
//            }
//            if(!userList.isNullOrEmpty()){
//                for (user in userList!!) {
//                    if (user.userName == userName) {
//                        val action =
//                            CreatePostFragmentDirections.actionCreatePostFragmentToProfileFragment()
//                        findNavController().navigate(action)
//                    }
//                }
//            }
        }

        binding.btnSelectPhoto.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            startActivityForResult(gallery, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            binding.ivPostImage.visibility = View.VISIBLE
            binding.ivPostImage.setImageURI(data?.data)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }

}



