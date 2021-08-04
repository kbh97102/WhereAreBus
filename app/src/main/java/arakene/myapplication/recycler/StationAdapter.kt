package arakene.myapplication.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arakene.myapplication.databinding.StationListItemBinding

class StationAdapter(var items : Array<HashMap<String, String>>): RecyclerView.Adapter<StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = StationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, null)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            //TODO Return to main screen with selected station info
        }
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}