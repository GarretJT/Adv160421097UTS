package com.example.Adv160421097UTS.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.Adv160421097UTS.databinding.FragmentHobbyDetailBinding
import com.example.Adv160421097UTS.model.Hobby
import com.example.Adv160421097UTS.viewmodel.DetailViewModel
//TODO: fix 'section' feature for main description list
class HobbyDetailFragment : Fragment() {

    private lateinit var binding: FragmentHobbyDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHobbyDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val hobbyId = arguments?.getString("hobbyID")
        Log.d("DetailFragment", "Received hobby ID: $hobbyId")
        if (hobbyId != null) {
            viewModel.fetch(hobbyId)
        }
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.constraintLayout.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.constraintLayout.visibility = View.VISIBLE
                }
            }
        })

        viewModel.hobbyLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                if (it) {
                    Toast.makeText(requireContext(), "Error loading hobby detail", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.hobbyLD.observe(viewLifecycleOwner, Observer { hobby ->
            hobby?.let {
                updateUI(hobby)
            }
        })
    }

    private fun updateUI(hobby: Hobby) {
        binding.textViewTitle.text = hobby.title
        binding.textViewUser.text = hobby.user
        binding.textViewShortDescription.text = hobby.shortDescription
        binding.textViewMainDescription.text = hobby.mainDescription[0] // Display the first section initially
    }

    // Function to display the next section of main description
    fun showNextSection(view: View) {
        // Increment the section index
        val currentSectionIndex = binding.textViewMainDescription.text.toString().toInt()
        val nextSectionIndex = currentSectionIndex + 1
        val hobby = viewModel.hobbyLD.value
        if (hobby != null && nextSectionIndex < hobby.mainDescription.size) {
            binding.textViewMainDescription.text = hobby.mainDescription[nextSectionIndex]
        }
    }

    // Function to display the previous section of main description
    fun showPreviousSection(view: View) {
        // Decrement the section index
        val currentSectionIndex = binding.textViewMainDescription.text.toString().toInt()
        val previousSectionIndex = currentSectionIndex - 1
        val hobby = viewModel.hobbyLD.value
        if (hobby != null && previousSectionIndex >= 0) {
            binding.textViewMainDescription.text = hobby.mainDescription[previousSectionIndex]
        }
    }
}
