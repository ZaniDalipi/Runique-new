package com.zanoapps.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.zanoapps.core.database.entities.DeletedRunSyncEntity
import com.zanoapps.core.database.entities.RunPendingSyncEntity
import com.zanoapps.core.domain.run.RunId

@Dao
interface RunPendingSyncDao {

    // CREATED RUN
    @Query("SELECT * FROM runpendingsyncentity WHERE userId = :userId")
    suspend fun getAllRunPendingSyncEntities(userId: String) : List<RunPendingSyncEntity>


    @Query("SELECT * FROM runpendingsyncentity WHERE runId = :runId")
    suspend fun getRunPendingSyncEntity(runId: String) : RunPendingSyncEntity?

    @Upsert
    suspend fun upsertRunPendingSyncEntity(entity: RunPendingSyncEntity)

    @Query("DELETE FROM runpendingsyncentity where runId = :runId")
    suspend fun deleteRunPendingSyncEntity(runId: String)


    // DELETED RUNS

    @Query("SELECT * From deletedrunsyncentity where userId = :userId")
    suspend fun getAllDeletedRunSyncEntities(userId: String): List<DeletedRunSyncEntity>

    @Upsert
    suspend fun upsertDeletedRunSyncEntity(entity: DeletedRunSyncEntity)

    @Query("Delete From deletedrunsyncentity where runId=:runId")
    suspend fun deleteDeletedRunSyncEntity(runId: RunId)
}