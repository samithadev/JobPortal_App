package database.repositories

import database.CompanyDatabase
import database.entities.Admin
import database.entities.Application

class ApplicationRepository(
    private  val db: CompanyDatabase
) {
    suspend fun insert(application: Application) = db.getApplicationDao().insert(application)
    suspend fun delete(application: Application) = db.getApplicationDao().delete(application)
    fun getAllApplications() =db.getApplicationDao().getAllApplications()
}