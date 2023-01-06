package com.graphicless.createaccountwithroomdatabase.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.graphicless.basiclinkedin.SharedPreference
import com.graphicless.createaccountwithroomdatabase.Constants
import com.graphicless.createaccountwithroomdatabase.R
import com.graphicless.createaccountwithroomdatabase.databinding.FragmentHomeBinding
import com.graphicless.createaccountwithroomdatabase.model.Authentication
import com.graphicless.createaccountwithroomdatabase.model.User
import com.graphicless.createaccountwithroomdatabase.viewmodel.UserViewModel
import java.util.*
import kotlin.concurrent.schedule


private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels()

    private lateinit var sharedPreference: SharedPreference
    private var userName:String? = null

    private val args: HomeFragmentArgs by navArgs()

    private var userList: List<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreference(requireContext())

//        sharedPreference.save("userName", "admin")

        if(args.userName == "none" && !sharedPreference.contains("userName")) {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = if(args.userName != "none")
            args.userName
        else{
            sharedPreference.getValueString("userName")
        }

//        Timer().schedule(1000) {
//            loadProfile()
//        }

//        binding.btnRefresh.setOnClickListener {
            Log.d(TAG, "Refresh button clicked outside of the live data")
            var userList: List<User>? = null
            viewModel.allUser.observe(viewLifecycleOwner) {
                userList = it
                Log.d(TAG, "Refresh button clicked inside the live data")
//            }
                if (!userList.isNullOrEmpty()) {
                    for (user in userList!!) {
                        if (user.userName == userName) {
                            Log.d(TAG, "Refresh button clikced inside the if block")
                            binding.tvUser.text =
                                user.userName.plus("\n").plus(user.firstName).plus("\n")
                                    .plus(user.lastName).plus("\n").plus(user.dob)
//                        sharedPreference.save("first_name", user.firstName.toString())
//                        sharedPreference.save("las_name", user.lastName.toString())
//                        sharedPreference.save("dob", user.dob.toString())
//                        sharedPreference.save("title", user.title.toString())
//                        sharedPreference.save("work_company", user.workCompany.toString())
//                        sharedPreference.save("work_place", user.workPlace.toString())
//                        sharedPreference.save("personal_website", user.personalWebsite.toString())
//                        sharedPreference.save("followers", user.followers.toString())
//                        sharedPreference.save("connections", user.connections.toString())
//                        sharedPreference.save("profile_pic", user.profilePic.toString())

                            Log.d(TAG, "refresh button clicked")
                            break
                        }
                    }
                }
            }
//        }



        binding.btnProfile.setOnClickListener {
//            if(!userList.isNullOrEmpty()){
//                for (user in userList!!) {
//                    if (user.userName == userName) {
//                        val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment(user)
//                        findNavController().navigate(action)
////                        break
//                    }
//                }
//            }

            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            findNavController().navigate(action)

        }

        binding.btnCreatePost.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreatePostFragment()
            findNavController().navigate(action)
        }

    }



    private fun showWelcomeMessage(user: Authentication) {
        Toast.makeText(requireContext(), "Welcome ${user.userName}", Toast.LENGTH_SHORT).show()
    }

}