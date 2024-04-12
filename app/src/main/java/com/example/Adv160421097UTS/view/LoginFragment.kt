package com.example.Adv160421097UTS.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.Adv160421097UTS.R
import com.example.Adv160421097UTS.databinding.FragmentLoginBinding
import com.example.Adv160421097UTS.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

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

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Authenticate user using ViewModel
            viewModel.authenticateUser(username, password)
        }

        // Observe login success event
        viewModel.loginSuccessLD.observe(viewLifecycleOwner) { success ->
            if (success) {
                // Show welcome message using Snackbar
                val username = binding.editTextUsername.text.toString()
                showWelcomeSnackbar(username)

                // Navigate to home/dashboard fragment upon successful login
                findNavController().navigate(R.id.actionHobbyList)
            }
        }

        // Observe login error event
        viewModel.loginErrorLD.observe(viewLifecycleOwner) { error ->
            if (error) {
                // Show error message for invalid credentials
                binding.textInputLayoutPassword.error = getString(R.string.invalid_credentials)
            }
        }

        // Observe loading status
        viewModel.loadingLD.observe(viewLifecycleOwner) { isLoading ->
            // Show loading indicator if data is being loaded
            if (isLoading) {
                // Show loading indicator
            } else {
                // Hide loading indicator
            }
        }

        binding.btnRegister.setOnClickListener {
            // Navigate to register fragment
            findNavController().navigate(R.id.actionLogin)
        }
    }

    private fun showWelcomeSnackbar(username: String) {
        Snackbar.make(
            requireView(),
            getString(R.string.welcome_message, username),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
