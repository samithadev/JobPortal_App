package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appsquad.EditGig
import com.example.appsquad.R
import database.CompanyDatabase
import database.entities.Gig
import database.repositories.GigRepository
import database.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Adapter for All Gigs

class AllGigAdapter: RecyclerView.Adapter<AllGigAdapter.ViewHolder>() {
    lateinit var data: List<Gig>
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvGigTitle: TextView
        var tvGigDesc: TextView
        var tvGigPrice: TextView
        var tvGigUser: TextView
        var tvGigId: TextView



        init {
            tvGigTitle = view.findViewById(R.id.tvAllGigTitle)
            tvGigDesc = view.findViewById(R.id.tvAllGigDesc)
            tvGigPrice = view.findViewById(R.id.tvAllGigSalary)
            tvGigUser = view.findViewById(R.id.tvAllGigUser)
            tvGigId = view.findViewById(R.id.tvAllGigId)


        }



    }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllGigAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.gig_item, parent, false)

        return AllGigAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AllGigAdapter.ViewHolder, position: Int) {
        holder.tvGigTitle.text = data[position].title.toString()
        holder.tvGigDesc.text = data[position].description.toString()
        holder.tvGigPrice.text = data[position].price.toString()
//        holder.tvGigUser.text = data[position].user.toString()
        holder.tvGigId.text = data[position].id.toString()

        val repository = UserRepository(CompanyDatabase.getInstance(context))
        CoroutineScope(Dispatchers.IO).launch {
            val dt = repository.getUserDetail(data[position].user.toInt())
            holder.tvGigUser.text = dt.name
        }



    }

    fun setData(data: List<Gig>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
}
