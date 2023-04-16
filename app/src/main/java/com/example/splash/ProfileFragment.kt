package com.example.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.splash.databinding.FragmentProfileBinding

private var _binding: FragmentProfileBinding? = null
private val binding get() = _binding!!
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root

        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)
        val nameTextView = view.findViewById(R.id.full_name) as TextView
        val emailTextView = view.findViewById(R.id.user_email) as TextView
        var firstName = this@ProfileFragment.activity?.intent?.extras?.getString("firstName")
        val lastName = this@ProfileFragment.activity?.intent?.extras?.getString("lastName")
        val email = this@ProfileFragment.activity?.intent?.extras?.getString("email")
        if (lastName?.length!! > 0) {
            firstName = "$firstName $lastName"
        }
        nameTextView.text = firstName
        emailTextView.text = email

        return view

    }

    fun profile_image() {

    }






}

