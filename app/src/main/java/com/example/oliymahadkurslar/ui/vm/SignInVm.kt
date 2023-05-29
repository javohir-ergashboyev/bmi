package com.example.oliymahadkurslar.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oliymahadkurslar.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInVm : ViewModel() {
    val usersList = MutableLiveData<List<UserModel>>()
    private val list = ArrayList<UserModel>()
    val db = Firebase.firestore
    fun getAllUsers() = viewModelScope.launch {
        val users = db.collection("users").get().await()
        users.documents.forEach {
            val m = UserModel(
                userName = it["userName"].toString(),
                userEmail = it["userEmail"].toString(),
                userPassword = it["userPassword"].toString(),
                userPhone = it["userPhone"].toString(),
            )
            list.add(m)
        }

        usersList.value = list

    }


}