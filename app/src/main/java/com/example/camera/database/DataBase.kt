package com.example.camera.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [UserImage::class], version = 1 , exportSchema = false)

@TypeConverters(Converters::class)

 abstract class DataBase : RoomDatabase(){
  abstract fun UserImageDao ():Dao
}