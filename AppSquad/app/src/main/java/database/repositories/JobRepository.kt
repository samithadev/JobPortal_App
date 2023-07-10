package database.repositories

import database.CompanyDatabase
import database.entities.Company
import database.entities.Job

class JobRepository(
    private  val db: CompanyDatabase
) {
    suspend fun insert(job: Job) = db.getJobDao().insertCompany(job)
    suspend fun delete(job: Job) = db.getJobDao().delete(job)
    fun getAllJobs() =db.getJobDao().getAllJobs()
    fun getCompanyJobs(compId:Int) = db.getJobDao().getCompanyJobs(compId )
    fun getAllJobCounts()= db.getJobDao().getAllJobCounts()
    fun getjobDetail(id:Int) = db.getJobDao().getJobDetail(id)
}