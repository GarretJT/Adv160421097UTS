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

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val loadingLD = MutableLiveData<Boolean>()
    val hobbyLoadErrorLD = MutableLiveData<Boolean>()
    val hobbyLD = MutableLiveData<Hobby>()

    private val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(application)
    }

    fun fetch(id: String) {
        loadingLD.value = true

        queue?.let { queue ->
            val url = "http://10.0.2.2/hobby/hobby_$id.json"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val hobbyType = object : TypeToken<Hobby>() {}.type
                    val result = Gson().fromJson<Hobby>(response, hobbyType)

                    hobbyLD.value = result
                    loadingLD.value = false
                    Log.d(TAG, "Response: $response")
                    Log.d(TAG, "Parsed Result: $result")
                },
                { error ->
                    Log.d(TAG, "Error: $error")
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
