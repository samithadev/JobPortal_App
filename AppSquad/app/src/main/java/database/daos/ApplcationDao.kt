package database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.entities.Admin
import database.entities.Application


@Dao
interface ApplcationDao {
    @Insert
    suspend fun insert(applcation: Application)
    @Delete
    suspend fun delete(applcation: Application)
    @Query("SELECT * From Application")
    fun getAllApplications(): List<Application>

    @Query("SELECT * FROM Application WHERE jobId=:jid")
    fun getJobApplications(jid:Int) : List<Application>

    @Query("SELECT * FROM Application WHERE user=:uid")
    fun getUserApplications(uid:Int) : List<Application>

    @Query("SELECT * FROM Application WHERE id=:id")
    fun getApplDetails(id:Int): Application

    @Query("UPDATE Application SET notes=:notes , contact=:contact WHERE id=:id")
    fun updateAppl(id:Int , notes :String , contact:String) : Int


}