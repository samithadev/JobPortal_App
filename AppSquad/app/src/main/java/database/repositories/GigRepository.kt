package database.repositories

import database.CompanyDatabase
import database.entities.Gig
import database.entities.Job

class GigRepository (
    private  val db: CompanyDatabase){

    suspend fun insert(gig: Gig) = db.getGigDao().insertGig(gig)
    suspend fun delete(gig: Gig) = db.getGigDao().delete(gig)
    fun getAllGigs() =db.getGigDao().getAllGigs()
    fun getUserGigs(userId:Int) = db.getGigDao().getUserGigs(userId)
    fun getgigDetails(id : Int) = db.getGigDao().getGigDetail(id)
    fun updateGig(title:String , description:String , price:String , id:Int) = db.getGigDao().updateGig(title, description, price, id)
    fun getGigCount() = db.getGigDao().getGigCount()
}