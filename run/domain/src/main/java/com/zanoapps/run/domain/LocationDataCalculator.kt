package com.zanoapps.run.domain

import com.zanoapps.core.location.LocationTimestamp
import kotlin.math.roundToInt
import kotlin.time.DurationUnit

object LocationDataCalculator {

    fun getTotalDistanceInMeters(locations: List<List<LocationTimestamp>>): Int {
        return locations.sumOf { timestampsPerLine ->
            timestampsPerLine.zipWithNext { location1, location2 ->
                location1.locationWithAltitude.location.distanceTo(location2.locationWithAltitude.location)
            }.sum().roundToInt()
        }
    }

    fun getMaxSpeedKmh(locations: List<List<LocationTimestamp>>): Double {
        return locations.maxOf { locationSet ->

            // in order to calc the speed we need to know the distance between 2 points an how long it took
            locationSet.zipWithNext { loc1, loc2 ->
                val distance = loc1.locationWithAltitude.location.distanceTo(
                    other = loc2.locationWithAltitude.location
                )
                val hoursDiff = (loc2.durationTimestamp - loc1.durationTimestamp)
                    .toDouble(DurationUnit.HOURS)

                if (hoursDiff == 0.0) {
                    0.0
                } else {
                    (distance / 1000.0) / hoursDiff
                }

            }.maxOrNull() ?: 0.0
        }
    }

    fun totalElevationMeters(locations: List<List<LocationTimestamp>>): Int {
        return locations.sumOf { locationSet ->
            locationSet.zipWithNext { loc1, loc2 ->
                val altitude1 = loc1.locationWithAltitude.altitude
                val altitude2 = loc2.locationWithAltitude.altitude

                (altitude2 - altitude1).coerceAtLeast(0.0)

            }.sum().roundToInt()
        }
    }
}