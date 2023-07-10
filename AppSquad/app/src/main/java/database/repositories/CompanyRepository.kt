package database.repositories

import android.content.ClipDescription
import database.AppSquadDatabase
import database.CompanyDatabase
import database.entities.Company

class CompanyRepository (
    private  val db: CompanyDatabase
    ){
    suspend fun insert(company: Company) = db.getCompanyDao().insertCompany(company)
    suspend fun delete(company: Company) = db.getCompanyDao().delete(company)
    fun getAllCompanies() =db.getCompanyDao().getAllCompanies()
    fun getAllCompaniesByAddress(address :String) = db.getCompanyDao().getAllCompaniesByAddress(address)
    fun getAllCompanyCounts() = db.getCompanyDao().getAllCompanyCounts()
    fun getCompanyLogin(email:String) = db.getCompanyDao().getCompanyLogin(email)
    fun getCompanyDetail(id:Int) = db.getCompanyDao().getCompanyDetail(id)
    fun updateCompany(id : Int , name : String , email :String , address: String , password:String , phone:String , regNo:String , description: String , companyImg:ByteArray) = db.getCompanyDao().updateCompany(id,name,email,address,password , phone, regNo, description , companyImg)
    fun dissaproveCompany(id: Int) = db.getCompanyDao().dissaproveCompany(id)
    fun getPendingCompanies() = db.getCompanyDao().getPendingCompanies()
    fun approveCompany(id: Int) = db.getCompanyDao().approveCompany(id)
    fun getPendingCompanyCounts() = db.getCompanyDao().getPendingCompanyCounts()
}