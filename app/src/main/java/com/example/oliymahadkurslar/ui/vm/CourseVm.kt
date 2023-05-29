package com.example.oliymahadkurslar.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oliymahadkurslar.model.CourseModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class CourseVm : ViewModel() {
    private val db = Firebase.firestore
    val courseData = MutableLiveData<List<CourseModel>>()
    private val list = ArrayList<CourseModel>()

    fun getAllData() = viewModelScope.launch {
        val result = db.collection("kurslar").get().await()
        result.forEach {
            val m = CourseModel(
                title = it["title"].toString(),
                desc = it["desc"].toString(),
                price = it["price"].toString(),
                status = it["status"].toString().toBoolean(),
                img = it["img"].toString(),
                id = it.id.toInt()
            )
            if (!list.contains(m)) {
                list.add(m)
            }
            courseData.value = list
        }

    }

}