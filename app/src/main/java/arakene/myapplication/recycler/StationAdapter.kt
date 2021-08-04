package arakene.myapplication.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StationAdapter(var items : Array<HashMap<String, String>>): RecyclerView.Adapter<StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = items.size
}