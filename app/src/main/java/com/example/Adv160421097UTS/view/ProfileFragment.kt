package com.example.Adv160421097UTS.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.Adv160421097UTS.R
import com.example.Adv160421097UTS.model.Users
import com.example.Adv160421097UTS.viewmodel.LoginViewModel

class ProfileFragment : Fragment() {

    private lateinit var textViewUsername: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var buttonChangePassword: Button
    private lateinit var buttonLogout: Button
    private lateinit var buttonChangeUsername: Button
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        textViewUsername = view.findViewById(R.id.textViewUsername)
        textViewEmail = view.findViewById(R.id.textViewEmail)

        buttonChangeUsername = view.findViewById(R.id.buttonChangeUsername)
        buttonChangePassword = view.findViewById(R.id.buttonChangePassword)
        buttonLogout = view.findViewById(R.id.buttonLogout)

        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        Log.d("ProfileFragment", "ViewModel initialized")

        loginViewModel.loggedInUserLD.observe(viewLifecycleOwner) { user ->
            Log.d("ProfileFragment", "Entering observer with user: $user")
            if (user != null) {
                Log.d("ProfileFragment", "User data: Username: ${user.username}, Email: ${user.email}")
                textViewUsername.text = "Username: ${user.username}"
                textViewEmail.text = "Email: ${user.email}"
            } else {
                Log.d("ProfileFragment", "No user is logged in")
                textViewUsername.text = "Username: N/A"
                textViewEmail.text = "Email: N/A"
            }
        }

        buttonChangeUsername.setOnClickListener {
            Toast.makeText(requireContext(), "Successfully changed username", Toast.LENGTH_SHORT).show()
        }

        buttonChangePassword.setOnClickListener {
            Toast.makeText(requireContext(), "Successfully changed password", Toast.LENGTH_SHORT).show()
        }

        buttonLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        return view
    }
}
