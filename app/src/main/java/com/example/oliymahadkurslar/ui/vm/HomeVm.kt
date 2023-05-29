package com.example.oliymahadkurslar.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oliymahadkurslar.model.NewsModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class HomeVm : ViewModel() {
    private val db = Firebase.firestore
    val courseData = MutableLiveData<List<NewsModel>>()
    private val list = ArrayList<NewsModel>()

    private val storage = FirebaseStorage.getInstance().reference
    private val imageRef = storage.child("images")

    fun getAllNews() = viewModelScope.launch {
        val query = db.collection("news").get().await()
        var id = ""
        var message = ""
        query.documents.forEach { i ->
            id = i.id
            message = i["message"].toString()
            val image = imageRef.child("$id.jpg").downloadUrl.await().toString()
            val m = NewsModel(
                image = image,
                message = message
            )
            if (!list.contains(m)) {
                list.add(m)
            }
            courseData.value = list.reversed()
        }

    }

}