package com.example.oliymahadkurslar.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oliymahadkurslar.model.VideoModel
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class VideosVm : ViewModel() {

    val videosList = MutableLiveData<List<VideoModel>>()
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private val videoRef = storageRef.child("videos")
    fun getVideos(id: Int): MutableLiveData<List<VideoModel>> {
        videoRef.child("${id}-kurs").listAll().addOnSuccessListener { listResult ->
            val videos = ArrayList<VideoModel>()
            for (item in listResult.items) {
                item.downloadUrl.addOnSuccessListener { url ->
                    videos.add(VideoModel(url.toString(), item.name))
                    videosList.value = videos
                }
            }

        }

        return videosList
    }

    fun getAllVideos(id: Int) = viewModelScope.launch {
        val videos = ArrayList<VideoModel>()
        val listRes = videoRef.child("${id}-kurs").listAll().await()
        for (i in listRes.items) {
            val url = i.downloadUrl.await()
            val name = i.name
            videos.add(VideoModel(url.toString(), name))
        }
        videosList.value=videos
    }


}