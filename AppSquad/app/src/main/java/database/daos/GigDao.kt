package database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.entities.Company
import database.entities.Gig
import database.entities.Job

//Sql queries
@Dao
interface GigDao {

    @Insert
    suspend fun insertGig(gig: Gig)
    @Delete
    suspend fun delete(gig: Gig)
    @Query("SELECT * From Gig")
    fun getAllGigs(): List<Gig>

    //
    @Query("SELECT * From Gig WHERE user =:userId")
    fun getUserGigs(userId : Int): List<Gig>

    @Query("SELECT * From Gig WHERE id = :id")
    fun getGigDetail(id : Int) : Gig

    //Update gig details
    @Query("UPDATE Gig SET title =:title ,description =:description , price =:price  WHERE id = :id")
    fun updateGig(title:String , description:String , price:String , id: Int) : Int

    //Count all the gigs
    @Query("SELECT COUNT(*) From Gig ")
    fun getGigCount() : Int

}
