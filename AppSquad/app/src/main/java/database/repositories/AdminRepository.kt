package database.repositories

import database.AppSquadDatabase
import database.CompanyDatabase
import database.entities.Admin
import database.entities.Company

class AdminRepository(
    private  val db: CompanyDatabase
) {
    suspend fun insert(admin: Admin) = db.getAdminDao().insertAdmin(admin)
    suspend fun delete(admin: Admin) = db.getAdminDao().delete(admin)
    fun getAdminLogin(email:String) =db.getAdminDao().getAdminLogin(email)
    fun getAllAdmin() =  db.getAdminDao().getAllAdmin()
    fun getAdminDetail(id:Int) = db.getAdminDao().getAdminDetail(id)
    fun updateAdmin(id:Int , email:String , password:String , profilePic:ByteArray) = db.getAdminDao().updateAdmin(id , email , password, profilePic)
}