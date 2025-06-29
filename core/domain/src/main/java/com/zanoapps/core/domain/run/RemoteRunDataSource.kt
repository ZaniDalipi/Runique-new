package com.zanoapps.core.domain.run

import com.zanoapps.core.domain.util.DataError
import com.zanoapps.core.domain.util.EmptyResult
import com.zanoapps.core.domain.util.Result
import javax.xml.crypto.Data

interface RemoteRunDataSource {

    suspend fun getRuns(): Result<List<Run>, DataError.Network>

    suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network >

    suspend fun deleteRun(id: String): EmptyResult<DataError.Network>
}