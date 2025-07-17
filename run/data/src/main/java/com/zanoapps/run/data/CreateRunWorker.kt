package com.zanoapps.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.zanoapps.core.database.dao.RunPendingSyncDao
import com.zanoapps.core.database.mappers.toRun
import com.zanoapps.core.domain.run.RemoteRunDataSource

class CreateRunWorker(
    context: Context,
    private val params: WorkerParameters,
    private val pendingSyncDao: RunPendingSyncDao,
    private val remoteRunDataSource: RemoteRunDataSource
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }

        val pendingRunId = params.inputData.getString(RUN_ID) ?: return Result.failure()
        val pendingRunEntity = pendingSyncDao.getRunPendingSyncEntity(pendingRunId)
            ?: return Result.failure()

        val run = pendingRunEntity.run.toRun()
        return when (val result =
            remoteRunDataSource.postRun(run, pendingRunEntity.mapPictureBytes)) {
            is com.zanoapps.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.zanoapps.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteRunPendingSyncEntity(pendingRunId)
                return Result.success()
            }
        }


    }

    companion object {
        const val RUN_ID = "RUN_ID"
    }
}