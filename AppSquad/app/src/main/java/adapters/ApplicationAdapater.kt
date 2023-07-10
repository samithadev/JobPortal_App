package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appsquad.EditApplication
import com.example.appsquad.R
import database.CompanyDatabase
import database.entities.Application
import database.repositories.ApplicationRepository
import database.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ApplicationAdapater: RecyclerView.Adapter<ApplicationAdapater.ViewHolder>()  {
    lateinit var data: List<Application>
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvNotes: TextView
        var tvContact: TextView
        var tvUser: TextView



        init {
            tvNotes = view.findViewById(R.id.tvNotes)
            tvContact = view.findViewById(R.id.tvContact)
            tvUser = view.findViewById(R.id.tvUser)


        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationAdapater.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.application_cpov, parent, false)

        return ApplicationAdapater.ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repositoryU = UserRepository(CompanyDatabase.getInstance(context))
        val us = data[position].user.toInt()
        CoroutineScope(Dispatchers.IO).launch {
            val dt = repositoryU.getUserDetail(us)
            holder.tvUser.text = dt.name.toString()
        }
//        holder.tvUser.text = data[position].user.toString()
        holder.tvNotes.text = data[position].notes.toString()
        holder.tvContact.text = data[position].contact.toString()


    }

    fun setData(data: List<Application>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
}