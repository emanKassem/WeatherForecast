package com.golyv.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "captured_images")
data class CapturedImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uri: String
)