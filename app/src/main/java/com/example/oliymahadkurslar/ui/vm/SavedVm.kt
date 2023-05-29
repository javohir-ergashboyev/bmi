package com.example.oliymahadkurslar.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oliymahadkurslar.db.SavedRep
import com.example.oliymahadkurslar.model.SavedModel
import kotlinx.coroutines.launch

class SavedVm(private val rep: SavedRep) : ViewModel() {
    fun save(course: SavedModel) = viewModelScope.launch {
        rep.insertCourse(course)
    }

    fun delete(course: SavedModel) = viewModelScope.launch {
        rep.deleteCourse(course)
    }

    fun getAllSaved() = rep.getAllFavoriteCourse()
}