package database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.entities.Admin
import database.entities.Company
import database.entities.Job
import database.entities.User


@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)
    @Delete
    suspend fun delete(user: User)
    @Query("SELECT * From User")
    fun getAllUsers(): List<User>

    @Query("SELECT * From User WHERE email LIKE :email")
    fun getUserLogin(email : String) : User

    @Query("SELECT * From User WHERE id = :id")
    fun getUserDetail(id : Int) : User

    @Query("SELECT COUNT(*) From User ")
    fun getUserCount() : Int
}