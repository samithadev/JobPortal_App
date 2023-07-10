package database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.entities.Company


@Dao
interface CompanyDao {
    @Insert
    suspend fun insertCompany(company: Company)
    @Delete
    suspend fun delete(company: Company)
    @Query("SELECT * From Company")
    fun getAllCompanies(): List<Company>

    @Query("SELECT * From Company WHERE (address LIKE :address OR name LIKE :address) AND approved = \'pending\'")
    fun getAllCompaniesByAddress(address :String) : List<Company>

    @Query("SELECT count(*) From Company")
    fun getAllCompanyCounts() : Int

    @Query("SELECT count(*) From Company WHERE approved = \'pending\'")
    fun getPendingCompanyCounts() : Int

    @Query("SELECT * From Company WHERE email LIKE :email")
    fun getCompanyLogin(email : String) : Company

    @Query("SELECT * From Company WHERE id = :id")
    fun getCompanyDetail(id : Int) : Company

    //edit company
    @Query("UPDATE Company SET name =:name ,email =:email , address =:address ,password = :password , phone = :phone , regNo =:regNo , description=:description , companyImg=:companyImg WHERE id = :id")
    fun updateCompany(id : Int , name : String , email :String , address: String , password:String , phone:String , regNo:String , description:String , companyImg:ByteArray) : Int

    
    //disapproving company
    @Query("UPDATE Company SET approved = \'disapproved\'  WHERE id = :id")
    fun dissaproveCompany(id : Int ) : Int

    //approving company
    @Query("UPDATE Company SET approved = \'approved\'  WHERE id = :id")
    fun approveCompany(id : Int ) : Int

   
    @Query("SELECT * From Company WHERE approved LIKE \'pending\'")
    fun getPendingCompanies(): List<Company>
}
