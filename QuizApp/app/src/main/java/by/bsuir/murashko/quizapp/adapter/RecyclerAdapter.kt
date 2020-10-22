package by.bsuir.murashko.quizapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.murashko.quizapp.R

class RecyclerAdapter(private val users: Set<Map.Entry<String, Any?>>) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.name)
        val scoresView: TextView = view.findViewById(R.id.scores)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false) as LinearLayout

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users.elementAt(position)
        holder.nameView.text = user.key
        holder.scoresView.text = user.value.toString()
    }

    override fun getItemCount() = users.size
}