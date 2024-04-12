package com.example.Adv160421097UTS.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Adv160421097UTS.databinding.FragmentHobbyListBinding
import com.example.Adv160421097UTS.viewmodel.ListViewModel

class HobbyListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private lateinit var hobbyListAdapter: HobbyListAdapter
    private lateinit var binding: FragmentHobbyListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHobbyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hobbyListAdapter = HobbyListAdapter(arrayListOf())

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = hobbyListAdapter

        observeViewModel()
    }


    private fun observeViewModel() {
        viewModel.hobbiesLD.observe(viewLifecycleOwner, Observer { hobbies ->
            hobbies?.let {
                Log.d(TAG, "Received hobbies: $it") // Log the received hobbies
                hobbyListAdapter.updateHobbies(it)
            }
        })

        viewModel.hobbyLoadErrorLD.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                binding.txtError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                binding.progressLoad.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.txtError.visibility = View.GONE
                    binding.recView.visibility = View.VISIBLE
                } else {
                    Log.d(TAG, "Loading finished.") // Log when loading finished
                }
            }
        })
    }

}
