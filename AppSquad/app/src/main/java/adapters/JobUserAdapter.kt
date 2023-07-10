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
import com.example.appsquad.ApplyJobForm
import com.example.appsquad.EditGig
import com.example.appsquad.R
import database.CompanyDatabase
import database.entities.Job
import database.repositories.CompanyRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobUserAdapter: RecyclerView.Adapter<JobUserAdapter.ViewHolder>() {

    lateinit var data: List<Job>
    lateinit var context: Context
    

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvUserJobTitle: TextView
        var tvUserJobSalary: TextView
        var tvUserJobReq: TextView
        var tvUserJobDes: TextView
        var tvUserJobComp: TextView
        var btnApply: Button

        init {
            tvUserJobTitle = view.findViewById(R.id.tvUserJobTitle)
            tvUserJobSalary = view.findViewById(R.id.tvUserJobSalary)
            tvUserJobReq = view.findViewById(R.id.tvUserJobReq)
            tvUserJobDes = view.findViewById(R.id.tvUserJobDesc)
            tvUserJobComp = view.findViewById(R.id.tvUserJobCompany)
            btnApply = view.findViewById(R.id.btnApplJob)
        }
        



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobUserAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_user_job, parent, false)

        return JobUserAdapter.ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvUserJobTitle.text = data[position].title.toString()
        holder.tvUserJobSalary.text = data[position].salary.toString()
        holder.tvUserJobReq.text = data[position].requirements.toString()
        holder.tvUserJobDes.text = data[position].description.toString()


        val repositoryCom = CompanyRepository(CompanyDatabase.getInstance(context))



        CoroutineScope(Dispatchers.IO).launch {
            val data1 = repositoryCom.getCompanyDetail(data[position].company)
            holder.tvUserJobComp.text = data1.name.toString()

        }
        holder.btnApply.setOnClickListener {
            var intent = Intent(context, ApplyJobForm::class.java)
            intent.putExtra("jobId" , data[position].id.toString())
            context.startActivity(intent)

        }

    }

    fun setData(data: List<Job>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
}
