package com.zanoapps.run.location

import android.location.Location
import com.zanoapps.core.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude{
    return LocationWithAltitude(
        location = com.zanoapps.core.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}