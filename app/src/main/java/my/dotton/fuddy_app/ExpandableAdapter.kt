package my.dotton.fuddy_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import my.dotton.fuddy_app.databinding.AreaItemRowBinding
import my.dotton.fuddy_app.databinding.LoadingItemBinding

data class AreaItem(
    var name:String ="",
    var date:String = "",
    var lnmadr:String = "",
    var vhcleCo:String = "",
    var primsnZoneRntfee:String = "",
    var weekdayTime:String = "",
    var wkendTime:String = "",
    var rstde:String = "",
    var lmttPrdlst:String = "",
    var institutionNm:String = "",
    var phoneNumber:String = "",
    var referenceDate:String = "",
    var isExpanded:Boolean = false
)

class ExpandableAdapter(private val context: Context,val itemList:ArrayList<AreaItem>) : Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private val VIEW_TYPE_ITEM = 0
        private val VIEW_TYPE_LOADING = 1
    }
    class LoadingViewHolder(private  val binding:LoadingItemBinding) : ViewHolder(binding.root){}
    class ExpandableViewHolder(private val binding: AreaItemRowBinding): ViewHolder(binding.root) {
        fun bind(areaItem: AreaItem, position: Int) {
            binding.areaItemTvName.text = areaItem.name
            binding.areaItemTvDate.text = areaItem.date

            binding.areaITemTvLnmadr.text = areaItem.lnmadr
            binding.areaItemTvVhcleCo.text = areaItem.vhcleCo
            binding.areaItemTvZoneRntFree.text = areaItem.primsnZoneRntfee
            binding.areaItemTvWeekdayTime.text = areaItem.weekdayTime
            binding.areaItemTvWkendTime.text = areaItem.wkendTime
            binding.areaItemTvRstde.text = areaItem.rstde
            binding.areaItemTvLmttPrdlst.text = areaItem.lmttPrdlst
            binding.areaItemTvInstitutionNm.text = areaItem.institutionNm
            binding.areaItemTvPhoneNumber.text = areaItem.phoneNumber
            binding.areaItemTvReferenceDate.text = areaItem.referenceDate

            binding.areaItemBtnArrow.setOnClickListener {
                areaItem.isExpanded = toggleLayout(!areaItem.isExpanded,it,binding.areaItemExpandedLayout)
            }
        }
        private fun toggleLayout(isExpanded: Boolean,view:View,layoutExpand: LinearLayout):Boolean{
            ToggleAnimation.toggleArrow(view,isExpanded)
            if(isExpanded) ToggleAnimation.expand(layoutExpand)
            else ToggleAnimation.collapse(layoutExpand)
            return isExpanded
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ExpandableViewHolder){
            holder.bind(itemList[holder.adapterPosition],position)
        }else { }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM -> {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val binding = AreaItemRowBinding.inflate(inflater, parent, false)
                ExpandableViewHolder(binding)
            }
            else -> {
                val inflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val binding = LoadingItemBinding.inflate(inflater, parent, false)
                LoadingViewHolder(binding)
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        //게시물과 로딩아이템의 기준 아이템 이름이 없을경우 로딩아이템
        return when(itemList[position].name){
            "" -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }
    override fun getItemCount(): Int {return itemList.size }

    fun setList(areaItems : MutableList<AreaItem>){
        itemList.addAll(areaItems)
        itemList.add(AreaItem("",""))
    }
    fun deleteLoading(){
        itemList.removeAt(itemList.lastIndex)
    }
}