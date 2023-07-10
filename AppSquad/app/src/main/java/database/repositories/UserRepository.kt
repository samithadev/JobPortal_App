package database.repositories

import database.CompanyDatabase
import database.entities.Job
import database.entities.User

class UserRepository(
    private  val db: CompanyDatabase
) {
    suspend fun insert(user: User) = db.getUserDao().insertUser(user)
    suspend fun delete(user: User) = db.getUserDao().delete(user)
    fun getAllUsers() =db.getUserDao().getAllUsers()
    fun getUserLogin(email:String) = db.getUserDao().getUserLogin(email)
    fun getUserDetail(id:Int) = db.getUserDao().getUserDetail(id)
    fun getUserCount() =  db.getUserDao().getUserCount()
}