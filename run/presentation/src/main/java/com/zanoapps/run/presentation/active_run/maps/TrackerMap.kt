@file:OptIn(MapsComposeExperimentalApi::class)

package com.zanoapps.run.presentation.active_run.maps

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.ktx.awaitSnapshot
import com.zanoapps.core.location.Location
import com.zanoapps.core.location.LocationTimestamp
import com.zanoapps.core.presentation.designsystem.RunIcon
import com.zanoapps.run.presentation.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TrackerMap(
    isRunFinished: Boolean,
    currentLocation: Location?,
    locations: List<List<LocationTimestamp>>,
    onSnapshot: (Bitmap) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mapStyle = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    }

    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState()

    val markerPositionLat by animateFloatAsState(
        targetValue = currentLocation?.lat?.toFloat() ?: 0f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )
    val markerPositionLong by animateFloatAsState(
        targetValue = currentLocation?.long?.toFloat() ?: 0f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )
    val markerPosition = remember(markerPositionLat, markerPositionLong) {
        LatLng(markerPositionLat.toDouble(), markerPositionLong.toDouble())
    }

    LaunchedEffect(markerPosition, isRunFinished) {
        if (!isRunFinished) {
            markerState.position = markerPosition
        }
    }

    LaunchedEffect(currentLocation, isRunFinished) {
        if (currentLocation != null && !isRunFinished) {
            val latLng = LatLng(currentLocation.lat, currentLocation.long)
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(latLng, 17f)
            )
        }
    }

    var triggerCapture by remember {
        mutableStateOf(false)
    }

    // with this we want to make sure that when on snapshot has been successfully create to cancel
    // it so we dont create more than one
    var createSnapshotJob: Job? = remember {
        null
    }

    // actual UI implementation
    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            mapStyleOptions = mapStyle
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        ),
        // we do this just so we are able to take the snapshot in the desired destination
        modifier = if (isRunFinished) {
            modifier
                .width(300.dp)
                .aspectRatio(16 / 9f)
                .alpha(0f)
                .onSizeChanged {
                    // a safety check to check if the size of the the modifier is 300 or less we capture, it successfully changed
                    if (it.width >= 300) {
                        triggerCapture = true
                    }
                }
        } else modifier


    ) {
        RuniquePolylines(locations = locations)

        MapEffect(locations, isRunFinished, triggerCapture, createSnapshotJob) { map ->
            if (isRunFinished && triggerCapture && createSnapshotJob == null) {
                triggerCapture = false


                // this is how we zoom in to the running polylines
                val boundBuilder = LatLngBounds.builder()
                locations.flatten().forEach { locationTimestamp ->
                    boundBuilder
                        .include(
                            LatLng(
                                locationTimestamp.locationWithAltitude.location.lat,
                                locationTimestamp.locationWithAltitude.location.long
                            )
                        )
                }

                // this is moving the camera so it adds some padding on all sides, so its not all crancked up
                map.moveCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        boundBuilder.build(),
                        100
                    )
                )


                // triggering the snapshot
                map.setOnCameraIdleListener {
                    createSnapshotJob?.cancel()
                    createSnapshotJob = GlobalScope.launch {
                        // make sure the map is sharp and focused before the snapshot
                        delay(500L)
                        map.awaitSnapshot()?.let(onSnapshot)
                    }
                }

            }

        }
        if (!isRunFinished && currentLocation != null) {
            MarkerComposable(
                currentLocation,
                state = markerState
            ) {
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = RunIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}