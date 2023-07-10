package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.appsquad.MainActivity
import com.example.appsquad.R
import database.AppSquadDatabase
import database.CompanyDatabase
import database.entities.Company
import database.repositories.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CompanyAdapter: RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    lateinit var data: List<Company>
    lateinit var context: Context

    class  ViewHolder(view: View):RecyclerView.ViewHolder(view){
        
        val cbCompany:CheckBox
        val dissapprove:ImageView
        val approve:ImageView
        val tvAddress:TextView
        val tvId:TextView
        val tvEmail:TextView
        val tvPassword:TextView

        init {
            cbCompany = view.findViewById(R.id.cbCompany)
            dissapprove = view.findViewById(R.id.btnImgDissapprove)
            approve = view.findViewById(R.id.btnImgApprove)
            tvAddress = view.findViewById(R.id.tvCompanyAddress)
            tvId = view.findViewById(R.id.tvId)
            tvEmail = view.findViewById(R.id.tvCompanyEmail)
            tvPassword = view.findViewById(R.id.tvCompanyPassword)
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)

        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//         updating details
         holder.cbCompany.text = data[position].name
        holder.tvAddress.text =data[position].address
        holder.tvId.text = data[position].id.toString()
        holder.tvEmail.text = data[position].email.toString()
        holder.tvPassword.text = data[position].password
        holder.dissapprove.setOnClickListener{
            if(holder.cbCompany.isChecked){
                val repository = CompanyRepository(CompanyDatabase.getInstance(context))
//                holder.dissapprove.setImageResource(R.drawable.clicked_delete_image)
                CoroutineScope(Dispatchers.IO).launch {
//                    repository.delete(data[position])
                    var id = data[position].id
                    if (id != null) {
                        repository.dissaproveCompany(id)
                    }
//                    data[position].id?.let { it1 -> repository.dissaproveCompany(it1) }
                    val data = repository.getPendingCompanies()
                    //val data2 = repository.getAllColomboCompanies()
                    withContext(Dispatchers.Main) {
                        setData(data, context)
//                        holder.ivDelete.setImageResource(R.drawable.delete_image)
                    }
                }
                Toast.makeText(context,"Company Disapproved",Toast.LENGTH_LONG)
                    .show()
            }else{
                Toast.makeText(context,"Cannot Dissaprove unmarked Company Request",Toast.LENGTH_LONG)
                    .show()
            }

        }

        holder.approve.setOnClickListener{
            if(holder.cbCompany.isChecked){
                val repository = CompanyRepository(CompanyDatabase.getInstance(context))
//                holder.dissapprove.setImageResource(R.drawable.clicked_delete_image)
                CoroutineScope(Dispatchers.IO).launch {
//                    repository.delete(data[position])
                    var id = data[position].id
                    if (id != null) {
                        repository.approveCompany(id)
                    }
//                    data[position].id?.let { it1 -> repository.dissaproveCompany(it1) }
                    val data = repository.getPendingCompanies()
                    //val data2 = repository.getAllColomboCompanies()
                    withContext(Dispatchers.Main) {
                        setData(data, context)
//                        holder.ivDelete.setImageResource(R.drawable.delete_image)
                    }
                }

                Toast.makeText(context,"Company Approved",Toast.LENGTH_LONG)
                    .show()
            }else{
                Toast.makeText(context,"Cannot approve unmarked Company Request",Toast.LENGTH_LONG)
                    .show()
            }

        }
    }

    fun setData(data: List<Company>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }



}
