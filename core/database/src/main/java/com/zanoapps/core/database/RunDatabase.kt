package com.zanoapps.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zanoapps.core.database.dao.RunDao
import com.zanoapps.core.database.dao.RunPendingSyncDao
import com.zanoapps.core.database.entities.DeletedRunSyncEntity
import com.zanoapps.core.database.entities.RunEntity
import com.zanoapps.core.database.entities.RunPendingSyncEntity

@Database(
    entities = [
        RunEntity::class,
        RunPendingSyncEntity::class,
        DeletedRunSyncEntity::class
               ],
    version = 1
)
abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
}