package com.fixdapp.internal.spacebook.persistence.daos

import androidx.room.*
import com.fixdapp.internal.spacebook.persistence.entities.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE sb_id=:sbId")
    suspend fun getUserById(sbId: Int): UserEntity

    @Query("SELECT * FROM user_table")
    suspend fun getUser(): UserEntity

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

}