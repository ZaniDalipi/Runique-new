package com.zanoapps.run.domain

import com.zanoapps.core.location.Location
import com.zanoapps.core.location.LocationTimestamp
import kotlin.time.Duration


data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationTimestamp>> = emptyList(),
)