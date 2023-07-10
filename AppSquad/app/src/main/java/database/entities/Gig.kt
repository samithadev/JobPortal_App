package database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

//Gig Entities
@Entity
data class Gig(
    var title:String,
    var description: String,
    var price:String,
    val user : Int

){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
