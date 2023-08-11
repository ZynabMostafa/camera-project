package com.example.camera.database

import android.content.Context
import androidx.room.Room

class BuilderClass(context: Context) {
private val convertersInstance = Converters()
   private val data by lazy {
        Room.databaseBuilder(context , DataBase::class.java , "myDataBase")
            .addTypeConverter(convertersInstance)
            .allowMainThreadQueries().build()
    }


    fun initDao():Dao{
        return data.UserImageDao()
    }
}