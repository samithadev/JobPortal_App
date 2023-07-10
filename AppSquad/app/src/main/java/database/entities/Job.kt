package database.entities

import android.content.ClipDescription
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Job(
    var title:String,
    var salary:String,
    var requirements:String,
    var description: String,
    var company:Int

){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
