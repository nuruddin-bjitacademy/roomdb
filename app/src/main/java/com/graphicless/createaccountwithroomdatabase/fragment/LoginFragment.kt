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
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.graphicless.basiclinkedin.SharedPreference
import com.graphicless.createaccountwithroomdatabase.Constants
import com.graphicless.createaccountwithroomdatabase.R
import com.graphicless.createaccountwithroomdatabase.databinding.FragmentLoginBinding
import com.graphicless.createaccountwithroomdatabase.model.Authentication
import com.graphicless.createaccountwithroomdatabase.viewmodel.UserViewModel

class LoginFragment : Fragment() {

    private var _binding:FragmentLoginBinding? = null
    private val binding get() = _binding!!

//    private val viewModel: UserViewModel by activityViewModels()

    private lateinit var viewModel: UserViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var authenticationList: List<Authentication>
    private lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = SharedPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[Constants.LOGIN_SUCCESSFUL] = false

        viewModel.allAuthentication.observe(viewLifecycleOwner){
            authenticationList = it
        }

        binding.btnLogin.setOnClickListener {
            val userName = binding.etUserName.text.toString()
            val password = binding.etPassword.text.toString()

            val auth = Authentication(userName, password)
            if(authenticationList.contains(auth)){
                sharedPreference.save("userName", userName)
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(userName)
                findNavController().navigate(action)

            }else{
                binding.etUserName.error = "Wrong username or password!!!"
                binding.etPassword.error = "Wrong username or password!!!"
            }
        }

        binding.tvCreateAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToCreateAccountFragment()
            findNavController().navigate(action)
        }

        binding.btnLoginAdmin.setOnClickListener {
            sharedPreference.save("userName", "admin")
        }
    }
}