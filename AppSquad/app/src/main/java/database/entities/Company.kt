package database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Company(
    //Company columns
    var name: String,
    var password:String,
    var  address: String,
    var email:String,
    var approved:String,
    var regNo:String,
    var phone:String,
    var description:String,
    var companyImg: ByteArray

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
