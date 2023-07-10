package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appsquad.EditGig
import com.example.appsquad.LoginCompany
import com.example.appsquad.R
import com.example.appsquad.UserDashboard
import database.CompanyDatabase
import database.entities.Gig
import database.entities.Job
import database.repositories.GigRepository
import database.repositories.JobRepository
import database.repositories.UserRepository
import kotlinx.coroutines.*

//User and Gig Adapter
class GigUserAdapter: RecyclerView.Adapter<GigUserAdapter.ViewHolder>() {
    lateinit var data: List<Gig>
    lateinit var context: Context

    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvGigTitle: TextView
        var tvGigDesc: TextView
        var tvGigPrice: TextView
        var tvGigUser: TextView
        var tvGigId:TextView
        var btnEditGig: Button
        var btnDeleteGig: Button


       init {
            tvGigTitle = view.findViewById(R.id.tvGigTitle)
            tvGigDesc = view.findViewById(R.id.tvGigDesc)
            tvGigPrice = view.findViewById(R.id.tvGigPrice)
            tvGigUser = view.findViewById(R.id.tvGigUser)
            btnEditGig = view.findViewById(R.id.btnEditGig)
            tvGigId = view.findViewById(R.id.tvGigIdd)
            btnDeleteGig = view.findViewById(R.id.btnDeleteGig)

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GigUserAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_gig, parent, false)

        return GigUserAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: GigUserAdapter.ViewHolder, position: Int) {
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

        holder.btnEditGig.setOnClickListener {

                var intent = Intent(context, EditGig::class.java)
                intent.putExtra("gigId" , data[position].id.toString())
//                data[position].id?.let { it1 -> intent.putExtra("gigId" , it1.toInt()) }
                context.startActivity(intent)



        }

        holder.btnDeleteGig.setOnClickListener {
            val repository = GigRepository(CompanyDatabase.getInstance(context))
            CoroutineScope(Dispatchers.IO).launch {
                repository.delete(data[position])
            }

            val intent = Intent(context, UserDashboard::class.java)
            Toast.makeText(context, "Gig Deleted...", Toast.LENGTH_SHORT).show()
            context.startActivity(intent)
        }

    }

    fun setData(data: List<Gig>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
}
