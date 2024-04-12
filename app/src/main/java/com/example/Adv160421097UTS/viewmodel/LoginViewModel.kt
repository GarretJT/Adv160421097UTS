package com.example.Adv160421097UTS.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.Adv160421097UTS.model.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loadingLD = MutableLiveData<Boolean>()
    val loginErrorLD = MutableLiveData<Boolean>()
    val loginSuccessLD = MutableLiveData<Boolean>()
    val loggedInUserLD = MutableLiveData<Users?>()

    private val TAG = "LoginViewModel"
    private var queue: RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(application)
    }

    fun authenticateUser(username: String, password: String) {
        loadingLD.value = true

        queue?.let { queue ->
            val url = "http://10.0.2.2/users/users.json"

            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response ->
                    val usersType = object : TypeToken<List<Users>>() {}.type
                    val userList = Gson().fromJson<List<Users>>(response, usersType)

                    // Check if the entered username and password match any user in the list
                    val authenticatedUser = userList.find { it.username == username && it.password == password }
                    if (authenticatedUser != null) {
                        // Authentication successful
                        Log.d(TAG, "User authenticated: $authenticatedUser")
                        loggedInUserLD.value = authenticatedUser // Update loggedInUserLD with the authenticated user
                        loginSuccessLD.value = true
                    } else {
                        // Authentication failed
                        Log.d(TAG, "Authentication failed for username: $username")
                        loginErrorLD.value = true
                    }
                    loadingLD.value = false
                },
                { error ->
                    Log.e(TAG, "Error: $error") // Log any error that occurred
                    loginErrorLD.value = true
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
