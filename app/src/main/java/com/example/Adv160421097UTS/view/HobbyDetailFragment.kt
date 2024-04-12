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

class HobbyDetailFragment : Fragment() {
    private lateinit var binding: FragmentHobbyDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var currentSectionIndex = 0

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

        binding.btnNext.setOnClickListener {
            showNextSection()
            updateSectionNumber()
        }

        binding.btnPrevious.setOnClickListener {
            showPreviousSection()
            updateSectionNumber()
        }

    }
    private fun updateSectionNumber() {
        val hobby = viewModel.hobbyLD.value
        val totalSections = hobby?.mainDescription?.size ?: 0
        val currentSectionNumber = currentSectionIndex + 1
        binding.textViewSectionNumber.text = "$currentSectionNumber of $totalSections"
    }
    private fun updateUI(hobby: Hobby) {
        binding.textViewTitle.text = hobby.title
        binding.textViewUser.text = hobby.user
        binding.textViewShortDescription.text = hobby.shortDescription
        binding.textViewMainDescription.text = hobby.mainDescription[currentSectionIndex]
    }

    private fun showNextSection() {
        val hobby = viewModel.hobbyLD.value
        if (hobby != null && currentSectionIndex < hobby.mainDescription.size - 1) {
            currentSectionIndex++
            binding.textViewMainDescription.text = hobby.mainDescription[currentSectionIndex]
        }
    }

    private fun showPreviousSection() {
        val hobby = viewModel.hobbyLD.value
        if (hobby != null && currentSectionIndex > 0) {
            currentSectionIndex--
            binding.textViewMainDescription.text = hobby.mainDescription[currentSectionIndex]
        }
    }
}
