package com.golyv.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CapturedImageDao {
    @Insert
    suspend fun insertString(stringEntity: CapturedImageEntity)

    @Query("SELECT * FROM captured_images")
    suspend fun getAllCapturedImages(): List<CapturedImageEntity>
}