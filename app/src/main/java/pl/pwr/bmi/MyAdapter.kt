package pl.pwr.bmi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val myDataSet: ArrayList<Result>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result: Result = myDataSet[position]

        holder.massTextView.text = result.mass
        holder.heightTextView.text = result.height
        holder.resultTextView.text = result.result
        holder.dateTextView.text = result.date
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.last_results_listitem, parent, false) as LinearLayout
        return MyViewHolder(v)
    }

    override fun getItemCount() = myDataSet.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val massTextView = itemView.findViewById(R.id.last_results_mass_textview) as TextView
        val heightTextView = itemView.findViewById(R.id.last_results_height_textview) as TextView
        val resultTextView = itemView.findViewById(R.id.last_results_result_textview) as TextView
        val dateTextView = itemView.findViewById(R.id.last_results_date) as TextView
    }
}