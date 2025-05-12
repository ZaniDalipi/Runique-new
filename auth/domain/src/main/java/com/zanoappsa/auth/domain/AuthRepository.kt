package com.zanoappsa.auth.domain

import com.zanoapps.core.domain.util.DataError
import com.zanoapps.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}