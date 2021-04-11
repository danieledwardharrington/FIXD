package com.fixdapp.internal.spacebook.persistence

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fixdapp.internal.spacebook.persistence.daos.UserDao
import com.fixdapp.internal.spacebook.persistence.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class SpacebookDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var databaseInstance: SpacebookDatabase? = null

        fun getDatabaseInstance(context: Context): SpacebookDatabase? {
            if (databaseInstance == null) {
                synchronized(SpacebookDatabase::class) {
                    databaseInstance = Room.databaseBuilder(
                        context.applicationContext,
                        SpacebookDatabase::class.java,
                        "spacebook_db.db"
                    ).fallbackToDestructiveMigration().build()
                }
            }

            return databaseInstance
        }
    }
}