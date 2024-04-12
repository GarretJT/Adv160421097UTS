package com.example.Adv160421097UTS.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.Adv160421097UTS.databinding.FragmentRegisterBinding
import com.example.Adv160421097UTS.viewmodel.RegisterViewModel

class RegisterFragment : Fragment() {

    private val TAG = "RegisterFragment"

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.buttonRegister.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            viewModel.registerUser(username, email, password)
        }

        binding.buttonLogin.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionLogin())
        }

        viewModel.registrationSuccessLD.observe(viewLifecycleOwner) { success ->
            if (success) {
                Log.d(TAG, "User registration successful")
                // Navigate to login fragment upon successful registration
                findNavController().navigateUp()
            }
        }

        // Observe registration error event
        viewModel.registrationErrorLD.observe(viewLifecycleOwner) { error ->
            if (error) {
                Log.e(TAG, "User registration failed")
                // Show error message for failed registration
            }
        }

        // Observe loading status
        viewModel.loadingLD.observe(viewLifecycleOwner) { isLoading ->
            // Show loading indicator if data is being loaded
            if (isLoading) {
                Log.d(TAG, "Loading...")
                // Show loading indicator
            } else {
                Log.d(TAG, "Loading finished")
                // Hide loading indicator
            }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigateUp()
    }
}
