package com.example.Adv160421097UTS.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.Adv160421097UTS.model.Hobby
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val hobbiesLD = MutableLiveData<ArrayList<Hobby>>()
    val hobbyLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(application)
    }

    fun refresh() {
        loadingLD.value = true
        hobbyLoadErrorLD.value = false

        queue?.let { queue ->
            val url = "http://10.0.2.2/hobby/hobby.json"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val hobbyType = object : TypeToken<List<Hobby>>() {}.type
                    val result = Gson().fromJson<List<Hobby>>(response, hobbyType)
                    hobbiesLD.value = result as ArrayList<Hobby>?
                    loadingLD.value = false
                    Log.d(TAG, "Response: $response") // Log the response from the server
                    Log.d(TAG, "Parsed Result: $result") // Log the parsed result
                },
                { error ->
                    Log.d(TAG, "Error: $error") // Log any error that occurred
                    hobbyLoadErrorLD.value = true
                    loadingLD.value = false
                })

            stringRequest.tag = TAG
            queue.add(stringRequest)
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
