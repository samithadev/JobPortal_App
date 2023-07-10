package database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Application(
    var notes:String,
    var jobId:Int,
    var user:Int,
    var contact:String

){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
