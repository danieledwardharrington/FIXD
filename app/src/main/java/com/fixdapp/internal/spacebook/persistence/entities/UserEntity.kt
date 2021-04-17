package com.fixdapp.internal.spacebook.persistence.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "user_table", indices = [Index(value = arrayOf("sb_id"), unique = true)])
data class UserEntity(
    @ColumnInfo(name = "sb_id") val sbId: Int,
    @ColumnInfo(name = "user_name") val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
