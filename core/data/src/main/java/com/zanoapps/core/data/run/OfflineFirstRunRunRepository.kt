package com.zanoapps.core.data.run

import com.zanoapps.core.domain.run.LocalRunDataSource
import com.zanoapps.core.domain.run.RemoteRunDataSource
import com.zanoapps.core.domain.run.RunRepository
import com.zanoapps.core.domain.run.Run
import com.zanoapps.core.domain.run.RunId
import com.zanoapps.core.domain.util.DataError
import com.zanoapps.core.domain.util.EmptyResult
import com.zanoapps.core.domain.util.Result
import com.zanoapps.core.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstRunRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope
) : RunRepository {

    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when (val result = remoteRunDataSource.getRuns()) {
            is Result.Error -> result.asEmptyDataResult()
            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRuns(result.data).asEmptyDataResult()
                }.await()
            }
        }

    }

    override suspend fun upsertRun(
        run: Run,
        mapPicture: ByteArray
    ): EmptyResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDataSource.postRun(
            run = runWithId,
            mapPicture = mapPicture
        )

        return when (remoteResult) {
            is Result.Error -> {
                return Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRun(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.delete(id)

        val remoteResult = applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()
    }
}