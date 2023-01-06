package com.graphicless.createaccountwithroomdatabase.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.graphicless.basiclinkedin.SharedPreference
import com.graphicless.createaccountwithroomdatabase.R
import com.graphicless.createaccountwithroomdatabase.adapter.PostAdapter
import com.graphicless.createaccountwithroomdatabase.databinding.FragmentHomeBinding
import com.graphicless.createaccountwithroomdatabase.databinding.FragmentProfileBinding
import com.graphicless.createaccountwithroomdatabase.model.User
import com.graphicless.createaccountwithroomdatabase.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.list_item.view.*
import java.io.ByteArrayOutputStream
import java.util.*

private const val TAG = "ProfileFragment"

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by activityViewModels()
//    private val args: ProfileFragmentArgs by navArgs()

    private lateinit var sharedPreference: SharedPreference

    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = sharedPreference.getValueString("userName")

        var userList: List<User>? = null

        viewModel.allUser.observe(viewLifecycleOwner) { it ->
            userList = it
            if (!userList.isNullOrEmpty()) {
                for (user in userList!!) {
                    if (user.userName == userName) {
                        currentUser = user

                        // setting profile
                        binding.name.text =
                            currentUser.firstName.plus(" ").plus(currentUser.lastName)
                        if (!Objects.equals(currentUser.coverPic, null)) {
                            try {
                                val imageBitmap = BitmapFactory.decodeByteArray(
                                    currentUser.coverPic,
                                    0,
                                    currentUser.coverPic!!.size
                                )
                                binding.coverPic.setImageBitmap(imageBitmap)
                            } catch (exception: Exception) {
                                Log.d("TAG", "exception: $exception")
                            }
                        }
                        if (!Objects.equals(currentUser.profilePic, null)) {
                            try {
                                val imageBitmap = BitmapFactory.decodeByteArray(
                                    currentUser.profilePic,
                                    0,
                                    currentUser.profilePic!!.size
                                )
                                binding.profilePic.setImageBitmap(imageBitmap)
                            } catch (exception: Exception) {
                                Log.d("TAG", "exception: $exception")
                            }
                        }

                        viewModel.allPost.observe(viewLifecycleOwner) {posts->
                            binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
                            binding.rvPost.adapter = PostAdapter(posts, currentUser)
                        }

                        // end
                        break
                    }
                }
            }
        }







        binding.coverPicEdit.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            startActivityForResult(gallery, 1000)
        }


        binding.profilePicEdit.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.type = "image/*"
            startActivityForResult(gallery, 2000)
        }


        binding.profileEdit.setOnClickListener {
//            findNavController().navigate(R.id.editProfileFragment)
            viewModel.allPost.observe(viewLifecycleOwner) {
                binding.rvPost.layoutManager = LinearLayoutManager(requireContext())
                binding.rvPost.adapter = PostAdapter(it, currentUser)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            binding.coverPic.setImageURI(data?.data)
            val bitmap = binding.coverPic.drawable.toBitmap()
//            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, binding.coverPic.width, bitmap.height, true)
            val imageByteArray = getBitmapAsByteArray(bitmap)
            val updatedUser = User(
                currentUser.userName,
                currentUser.firstName,
                currentUser.lastName,
                currentUser.dob,
                currentUser.title,
                currentUser.workCompany,
                currentUser.workPlace,
                currentUser.personalWebsite,
                currentUser.followers,
                currentUser.connections,
                currentUser.profilePic,
                imageByteArray
            )
            viewModel.updateUser(updatedUser)
        } else if (requestCode == 2000 && resultCode == Activity.RESULT_OK) {
            binding.profilePic.setImageURI(data?.data)
            val bitmap = binding.profilePic.drawable.toBitmap()
//            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, binding.profilePic.width, binding.profilePic.height, true)
            val imageByteArray = getBitmapAsByteArray(bitmap)
            val updatedUser = User(
                currentUser.userName,
                currentUser.firstName,
                currentUser.lastName,
                currentUser.dob,
                currentUser.title,
                currentUser.workCompany,
                currentUser.workPlace,
                currentUser.personalWebsite,
                currentUser.followers,
                currentUser.connections,
                imageByteArray,
                currentUser.coverPic
            )
            viewModel.updateUser(updatedUser)
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