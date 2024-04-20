package com.example.Adv160421097UTS.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.Adv160421097UTS.R
import com.example.Adv160421097UTS.databinding.FragmentRegisterBinding
import com.example.Adv160421097UTS.viewmodel.RegisterViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

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
            findNavController().navigate(R.id.actionLogin)
        }

        viewModel.registerSuccessLD.observe(viewLifecycleOwner) { success ->
            if (success) {
                showSuccessSnackbar()
                findNavController().navigate(R.id.actionLogin)
            }
        }

        viewModel.registerErrorLD.observe(viewLifecycleOwner) { error ->
            if (error.isNotBlank()) {
                Snackbar.make(
                    requireView(),
                    error,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.loadingLD.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
            } else {
            }
        }
    }

    private fun showSuccessSnackbar() {
        Snackbar.make(
            requireView(),
            getString(R.string.registration_success_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
