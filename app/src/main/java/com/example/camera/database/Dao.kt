package com.example.camera.database

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface Dao {

    @Query("SELECT image FROM userimagetable")
    fun getAllImage():List<Bitmap>


     @Insert
     fun insetImage(userImage: UserImage)

}