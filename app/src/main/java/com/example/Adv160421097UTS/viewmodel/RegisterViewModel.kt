package com.example.Adv160421097UTS.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.Adv160421097UTS.model.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val loadingLD = MutableLiveData<Boolean>()
    val registerSuccessLD = MutableLiveData<Boolean>()
    val registerErrorLD = MutableLiveData<String>()

    private val TAG = "RegisterViewModel"
    private var queue: RequestQueue? = null

    init {
        queue = Volley.newRequestQueue(application)
    }

    fun registerUser(username: String, email: String, password: String) {
        loadingLD.value = true

        val url = "http://10.0.2.2/users/users.php"

        val newUser = Users(0, username, email, password)

        val newUserJson = Gson().toJson(newUser)

        val postRequest = object : StringRequest(
            Request.Method.POST,
            url,
            { response ->
                Log.d(TAG, "Response received from update request: $response")
                registerSuccessLD.value = true
            },
            { error ->
                Log.e(TAG, "Error updating user data: $error")
                registerErrorLD.value = "Failed to update user data: $error"
            }
        ) {
            override fun getBody(): ByteArray {
                return newUserJson.toByteArray(Charsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        queue?.add(postRequest)

        loadingLD.value = false
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}
