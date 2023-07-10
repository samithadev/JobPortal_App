package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import database.daos.AdminDao
import database.daos.CompanyDao
import database.entities.Admin
import database.entities.Company


@Database(entities = [Company::class , Admin::class] , version = 1 )

abstract class AppSquadDatabase:RoomDatabase() {
    abstract fun getCompanyDao(): CompanyDao
    abstract fun getAdminDao(): AdminDao

    companion object {
        @Volatile
        private var INSTANCE: AppSquadDatabase? = null

        fun getInstance(context: Context): AppSquadDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppSquadDatabase::class.java,
                    "company1_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}