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
import com.example.appsquad.EditJob
import com.example.appsquad.JobApplications
import com.example.appsquad.R
import database.CompanyDatabase
import database.entities.Company
import database.entities.Job
import database.repositories.CompanyRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class JobAdapter: RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    lateinit var data: List<Job>
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvJobTitle: TextView
        var tvJobSalary: TextView
        var tvJobReq: TextView
        var tvJobDes: TextView
        var tvJobComp: TextView
        var btnDeleteJob:Button
        var btnViewApplication:Button
        var btnGoEditjob:Button
        

        init {
            tvJobTitle = view.findViewById(R.id.tvJobTitle)
            tvJobSalary = view.findViewById(R.id.tvJobSalary)
            tvJobReq = view.findViewById(R.id.tvJobReq)
            tvJobDes = view.findViewById(R.id.tvJobDesc)
            tvJobComp = view.findViewById(R.id.tvJobComp)
            btnDeleteJob = view.findViewById(R.id.btnDeleteJob)
            btnViewApplication = view.findViewById(R.id.btnViewApplications)
            btnGoEditjob = view.findViewById(R.id.btnGoEditJob)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_job_cpov, parent, false)

        return JobAdapter.ViewHolder(view)
    }
    


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvJobTitle.text = data[position].title.toString()
        holder.tvJobSalary.text = data[position].salary.toString()
        holder.tvJobReq.text = data[position].requirements.toString()
        holder.tvJobDes.text = data[position].description.toString()
//        holder.tvJobComp.text = data[position].company.toString()

        val repositoryC = CompanyRepository(CompanyDatabase.getInstance(context))
        CoroutineScope(Dispatchers.IO).launch {
            val comp = data[position].company?.let { repositoryC.getCompanyDetail(it) }
            if (comp != null) {
                holder.tvJobComp.text = comp.name
            }
        }



        holder.btnViewApplication.setOnClickListener {
            var intent = Intent(context, JobApplications::class.java)
            intent.putExtra("jobId" , data[position].id.toString())
            context.startActivity(intent)
        }

        holder.btnDeleteJob.setOnClickListener {
            val repository = JobRepository(CompanyDatabase.getInstance(context))
            CoroutineScope(Dispatchers.IO).launch {
                repository.delete(data[position])

                val sharedPreferences = context.getSharedPreferences("MySession",
                    AppCompatActivity.MODE_PRIVATE
                )
                val cookies = sharedPreferences.getString("company", null)

                val data1 = cookies?.let { it1 -> repository.getCompanyJobs(it1.toInt()) }
                withContext(Dispatchers.Main) {
                    if (data1 != null) {
                        setData(data1, context)
                    }

                }

            }
        }

        holder.btnGoEditjob.setOnClickListener {
            var intent = Intent(context, EditJob::class.java)
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


