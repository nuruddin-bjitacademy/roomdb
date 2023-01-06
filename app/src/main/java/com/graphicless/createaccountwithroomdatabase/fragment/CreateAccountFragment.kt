package com.graphicless.createaccountwithroomdatabase.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.graphicless.createaccountwithroomdatabase.databinding.FragmentCreateAccountBinding
import com.graphicless.createaccountwithroomdatabase.model.Authentication
import com.graphicless.createaccountwithroomdatabase.model.User
import com.graphicless.createaccountwithroomdatabase.viewmodel.UserViewModel


private const val TAG = "CreateAccountFragment"
class CreateAccountFragment : Fragment() {

    private var _binding:FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UserViewModel

    private var userNameList:List<String>? = null
    private var dob:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCreateAccountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allUserName?.observe(viewLifecycleOwner){
            userNameList = it
        }
//        binding.etUserName.addTextChangedListener {
//            if(!"[a-z0-9]+".toRegex().matches(it.toString())){
//                binding.etUserName.error = "user name can only be lower case and numbers"
//            }
//            if(userNameList!!.contains(it.toString())){
//                binding.etUserName.error = "Username exits."
//                binding.btnCreateAccount.isEnabled = false
//            }else
//                binding.btnCreateAccount.isEnabled = true
//        }

        binding.etPassword.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val passwordLength = binding.etPassword.text.toString().length
                if(passwordLength<8){
                    binding.etPassword.error = "Password must be at least 8 character long!"
                }
            }
        }

        binding.tilDob.setOnClickListener {
            Log.d(TAG, "onViewCreated: called")
            binding.datePicker.visibility = View.VISIBLE
            binding.btnOk.visibility = View.VISIBLE
        }


        binding.btnOk.setOnClickListener {
            val day: Int = binding.datePicker.dayOfMonth
            val month: Int = binding.datePicker.month + 1
            val year: Int = binding.datePicker.year
            dob = "$day.$month.$year"
            val dobText = "Date of Birth: $dob"
            binding.tilDob.text = dobText
            binding.datePicker.visibility = View.GONE
            binding.btnOk.visibility = View.GONE
        }

        binding.btnCreateAccount.setOnClickListener {
            val userName = binding.etUserName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            var firstName: String? = binding.etFirstName.text.toString().trim()
            var lastName: String? = binding.etLastName.text.toString().trim()

            if(!"[a-z0-9]+".toRegex().matches(userName)){
                Toast.makeText(requireContext(), "Invalid user name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if(password.length<8 || confirmPassword != password){
                if(confirmPassword != password)
                    binding.etConfirmPassword.error = "Password doesn't match"
                return@setOnClickListener
            }


            if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
                if(TextUtils.isEmpty(userName))
                    binding.etUserName.error = "This field is required"
                if(TextUtils.isEmpty(password))
                    binding.etPassword.error = "This field is required"
                if(TextUtils.isEmpty(confirmPassword))
                    binding.etConfirmPassword.error = "This field is required"
            }else{
                val auth = Authentication(userName, password)
                viewModel.addAuth(auth)
                if(TextUtils.isEmpty(firstName))
                    firstName = null
                if(TextUtils.isEmpty(lastName))
                    lastName = null
                if(TextUtils.isEmpty(dob))
                    dob = null
                val user = User(userName, firstName, lastName, dob, null, null, null, null, null, null,null, null)
                viewModel.addUser(user)
            }

            val action = CreateAccountFragmentDirections.actionCreateAccountFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.tvLogin.setOnClickListener {
            val action = CreateAccountFragmentDirections.actionCreateAccountFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

}