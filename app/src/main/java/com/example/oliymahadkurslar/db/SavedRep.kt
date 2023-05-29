package com.example.oliymahadkurslar.db

import com.example.oliymahadkurslar.model.SavedModel

class SavedRep(val db: SavedDatabase) {

    suspend fun insertCourse(course: SavedModel) = db.getSaved().insertCourse(course)
    suspend fun deleteCourse(course: SavedModel) = db.getSaved().deleteCourse(course)
    fun getAllFavoriteCourse() = db.getSaved().getAllSavedCourse()
}