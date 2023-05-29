package com.example.oliymahadkurslar.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.oliymahadkurslar.model.SavedModel

@Dao
interface SavedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: SavedModel)

    @Delete()
    suspend fun deleteCourse(course: SavedModel)


    @Query("SELECT * FROM saved_courses")
    fun getAllSavedCourse(): LiveData<List<SavedModel>>

}