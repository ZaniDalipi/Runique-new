package com.zanoapps.core.database.mappers

import com.zanoapps.core.database.entities.RunEntity
import com.zanoapps.core.domain.run.Run
import com.zanoapps.core.location.Location
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Duration.Companion.milliseconds

fun RunEntity.toRun(): Run {
    return Run(
        id = id,
        duration = durationMillis.milliseconds,
        dateTimeUtc = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC")),
        location = Location(
            lat = lat,
            long = long
        ),
        maxSpeedKmh = maxSpeedKmh,
        totalElevationMeters = totalElevationInMeters,
        mapPictureUrl = mapPictureUrl,
        distanceMeters = distanceMeters
    )
}


fun Run.toRunEntity(): RunEntity {
    return RunEntity(
        id = id ?: ObjectId().toHexString(),
        durationMillis = duration.inWholeMilliseconds,
        maxSpeedKmh = maxSpeedKmh,
        dateTimeUtc = dateTimeUtc.toInstant().toString(),
        lat = location.lat,
        long = location.long,
        distanceMeters = distanceMeters,
        avgSpeedKmh = avgSpeedKmh,
        totalElevationInMeters = totalElevationMeters,
        mapPictureUrl = mapPictureUrl
    )
}

