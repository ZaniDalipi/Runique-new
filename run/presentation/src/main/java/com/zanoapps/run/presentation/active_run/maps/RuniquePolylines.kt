package com.zanoapps.run.presentation.active_run.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline
import com.zanoapps.core.location.LocationTimestamp

@Composable
fun RuniquePolylines(
    locations: List<List<LocationTimestamp>>,
) {
    // we want to take the list of list of locations and turn that intro polylines
    // this block will be executed whenever the location changes and we remember the value
    val polylines =
        remember(locations) {
            locations.map {
                it.zipWithNext { tmp1, tmp2 ->
                    PolylineUI(
                        location1 = tmp1.locationWithAltitude.location,
                        location2 = tmp2.locationWithAltitude.location,
                        color = PolylineColorCalculator.locationsToColor(
                            location1 = tmp1,
                            location2 = tmp2
                        )
                    )
                }
            }
        }
    polylines.forEach { polyline ->
        polyline.forEach { polylineUi ->
            Polyline(
                points = listOf(
                    LatLng(polylineUi.location1.lat, polylineUi.location1.long),
                    LatLng(polylineUi.location2.lat, polylineUi.location2.long)
                ),
                color = polylineUi.color,
                jointType = JointType.BEVEL
            )
        }
    }
}