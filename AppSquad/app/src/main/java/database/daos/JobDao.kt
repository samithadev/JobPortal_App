package database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.entities.Company
import database.entities.Job


@Dao
interface JobDao {

    @Insert
    suspend fun insertCompany(job: Job)
    @Delete
    suspend fun delete(job: Job)
    @Query("SELECT * From Job")
    fun getAllJobs(): List<Job>

    @Query("SELECT * From Job WHERE company =:compId")
    fun getCompanyJobs(compId : Int): List<Job>

    @Query("SELECT count(*) From Job")
    fun getAllJobCounts() : Int

    @Query("SELECT * From Job WHERE id = :id")
    fun getJobDetail(id : Int) : Job
}
