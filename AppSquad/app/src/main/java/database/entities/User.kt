package database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Entites for User
@Entity
data class User(
    var email:String,
    var password:String,
    var name:String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
