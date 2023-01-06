package com.graphicless.createaccountwithroomdatabase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.graphicless.createaccountwithroomdatabase.R
import com.graphicless.createaccountwithroomdatabase.databinding.FragmentEditProfileBinding
import com.graphicless.createaccountwithroomdatabase.viewmodel.UserViewModel

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        binding.btnClose.setOnClickListener{
            (requireActivity() as AppCompatActivity).supportActionBar?.show()
            findNavController().navigate(R.id.profileFragment)
        }
    }

}