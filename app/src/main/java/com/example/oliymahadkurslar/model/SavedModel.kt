package com.example.oliymahadkurslar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "saved_courses"
)

data class SavedModel(
    @PrimaryKey
    var id: Int? = null,
    val course_id: Int,
    val desc: String,
    val img: String,
    val price: String,
    val title: String,
)
