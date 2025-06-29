package com.zanoapps.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity
data class RunEntity(
    val durationMillis: Long,
    val distanceMeters: Int,
    val dateTimeUtc: String,
    val lat: Double,
    val long: Double,
    val avgSpeedKmh: Double,
    val maxSpeedKmh: Double,
    val totalElevationInMeters: Int,
    val mapPictureUrl: String?,

    @PrimaryKey(autoGenerate = false)
    // converting this using bson becauase the backend is in MongoDB
    val id: String = ObjectId().toHexString()
)
