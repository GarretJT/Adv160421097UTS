package com.example.Adv160421097UTS.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.Adv160421097UTS.R
import com.example.Adv160421097UTS.databinding.FragmentLoginBinding
//TODO: implement login system using database (json)
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Check if username and password are valid
            if (isValidCredentials(username, password)) {
                // Navigate to home/dashboard fragment
                findNavController().navigate(R.id.actionHobbyList)
            } else {
                // Show error message for invalid credentials
                binding.textInputLayoutPassword.error = getString(R.string.invalid_credentials)
            }
        }

        binding.btnRegister.setOnClickListener {
            // Navigate to register fragment
            findNavController().navigate(R.id.actionLogin)
        }
    }

    private fun isValidCredentials(username: String, password: String): Boolean {
        // Dummy implementation: Check if username and password match hardcoded values
        return username == "user" && password == "password"
    }
}
