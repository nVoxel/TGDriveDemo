package com.voxeldev.tgdrive.database.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author nvoxel
 */
@Entity(tableName = "favorites", indices = [Index(value = ["chat_id", "message_id"], unique = true)])
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "chat_id")
    val chatId: Long,
    @ColumnInfo(name = "message_id")
    val messageId: Long,
)
