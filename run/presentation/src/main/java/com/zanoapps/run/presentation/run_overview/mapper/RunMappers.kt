package com.zanoapps.run.presentation.run_overview.mapper

import com.zanoapps.core.domain.run.Run
import com.zanoapps.core.presentation.ui.formatted
import com.zanoapps.core.presentation.ui.toFormattedKilometers
import com.zanoapps.core.presentation.ui.toFormattedKmh
import com.zanoapps.core.presentation.ui.toFormattedMeters
import com.zanoapps.core.presentation.ui.toFormattedPace
import com.zanoapps.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {

    val dateTimeToLocalTime = dateTimeUtc
        .withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter
        .ofPattern("MMM dd, yyyy - hh:mma")
        .format(dateTimeToLocalTime)

    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKilometers(),
        avgSpeed = avgSpeedKmh.toFormattedKmh(),
        maxSpeed = maxSpeedKmh.toFormattedKmh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}