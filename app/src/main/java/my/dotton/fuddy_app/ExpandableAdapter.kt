package my.dotton.fuddy_app

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ExpandableAdapter:RecyclerView.Adapter<ExpandableAdapter.ExpandableViewHolder>() {
    class ExpandableViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}