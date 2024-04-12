package com.example.Adv160421097UTS.view
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.Adv160421097UTS.R
import com.example.Adv160421097UTS.databinding.FragmentHobbyListItemBinding
import com.example.Adv160421097UTS.model.Hobby
import com.example.Adv160421097UTS.util.loadImageWithProgressBar


class HobbyListAdapter(
    private val hobbyList: ArrayList<Hobby>
) : RecyclerView.Adapter<HobbyListAdapter.HobbyViewHolder>() {

    class HobbyViewHolder(val binding: FragmentHobbyListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbyViewHolder {
        val binding = FragmentHobbyListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HobbyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HobbyViewHolder, position: Int) {
        Log.d("HobbyListAdapter", "Title: ${hobbyList[position].title}, User: ${hobbyList[position].user}, Short Description: ${hobbyList[position].shortDescription}")

        holder.binding.textViewTitle.text = hobbyList[position].title
        holder.binding.textViewUser.text = hobbyList[position].user
        holder.binding.textViewShortDescription.text = hobbyList[position].shortDescription

        // Set an onClickListener for the item
        holder.binding.root.setOnClickListener {
            val action = HobbyListFragmentDirections.actionHobbyDetail(hobbyList[position].id)
            Log.d("HobbyListAdapter", "HOBBY ID: ${hobbyList[position].id}")

            Navigation.findNavController(it).navigate(action)
        }

        var imageView = holder.itemView.findViewById<ImageView>(R.id.imageViewHobby)
        var progressBar = holder.itemView.findViewById<ProgressBar>(R.id.progressBar)
        val hobbyItem = hobbyList.getOrNull(position)
        if (hobbyItem != null) {
            imageView.loadImageWithProgressBar(hobbyItem.image, progressBar)
        } else {
            // Handle the case where hobbyItem is null
        }

    }

    override fun getItemCount(): Int {
        return hobbyList.size
    }

    fun updateHobbies(newHobbyList: List<Hobby>) {
        hobbyList.clear()
        hobbyList.addAll(newHobbyList)
        notifyDataSetChanged()

        // Add a log statement to check if the function is being called
        Log.d("HobbyListAdapter", "updateHobbies called with ${newHobbyList.size} hobbies")
    }

}