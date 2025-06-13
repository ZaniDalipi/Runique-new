package com.zanoapps.run.domain

import com.zanoapps.core.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

// this will observe the location all the time
interface LocationObserver {
    fun observeLocation(interval: Long): Flow<LocationWithAltitude>
}