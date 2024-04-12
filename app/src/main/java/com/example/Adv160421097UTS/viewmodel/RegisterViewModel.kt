package com.example.Adv160421097UTS.viewmodel
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.Adv160421097UTS.model.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "RegisterViewModel"

    val registrationSuccessLD = MutableLiveData<Boolean>()
    val registrationErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    fun registerUser(username: String, email: String, password: String) {
        loadingLD.value = true

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val usersList = readUsersListFromJson()

                // Auto-increment user ID
                val userId = usersList.maxByOrNull { it.id }?.id?.plus(1) ?: 1

                val newUser = Users(userId, username, email, password)
                usersList.add(newUser)

                val success = writeUsersListToJson(usersList)
                loadingLD.postValue(false)

                if (success) {
                    registrationSuccessLD.postValue(true)
                } else {
                    registrationErrorLD.postValue(true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error registering user: ${e.message}")
                registrationErrorLD.postValue(true)
                loadingLD.postValue(false)
            }
        }
    }

    private suspend fun readUsersListFromJson(): MutableList<Users> = withContext(Dispatchers.IO) {
        val json = getApplication<Application>().assets.open("users.json").bufferedReader().use { it.readText() }
        val usersListType = object : TypeToken<MutableList<Users>>() {}.type
        Gson().fromJson(json, usersListType) ?: mutableListOf()
    }

    private suspend fun writeUsersListToJson(usersList: MutableList<Users>): Boolean = withContext(Dispatchers.IO) {
        try {
            val json = Gson().toJson(usersList)
            val file = getApplication<Application>().filesDir.resolve("users.json")
            file.writeText(json)
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error writing users list to JSON file: ${e.message}")
            false
        }
    }
}
