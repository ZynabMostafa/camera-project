package com.example.camera.database

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserImageTable")
data class UserImage(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val image : Bitmap?)
