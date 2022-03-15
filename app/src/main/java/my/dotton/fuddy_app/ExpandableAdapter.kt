package my.dotton.fuddy_app

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import my.dotton.fuddy_app.databinding.AreaItemRowBinding
import my.dotton.fuddy_app.databinding.LoadingItemBinding
import my.dotton.fuddy_app.databinding.WeatherchartItemBinding

//    prmisnZoneNm 허가구역명
//    ctprvnNm  시도명
//    signguNm 시군구명
//    lnmadr 소재지지번주소
//    latitude 위도
//    longitude 경도
//    vhcleCo 푸드트럭운영대수
//    primsnZoneRntfee 허가구역 사용료
//    beginDate 허가구역운영시작일자
//    endDate 허가구역운영종료일자
//    rstde 허가구역휴무일
//    weekdayOperOpenHhmm 허가구역평일운영시작시각
//    weekdayOperColseHhmm 허가구역평일운영종료시각
//    wkendOperOpenHhmm 허가구역주말운영시작시각
//    wkendOperColseHhmm 허가구역주말운영종료시각
//    lmttPrdlst 판매제한품목
//    institutionNm 관리기관명
//    phoneNumber 관리기관 전화번호
//    referenceDate 데이터 기준일자
data class AreaItem(
    var name:String ="",
    var date:String = "",
    var isExpanded:Boolean = false
)

class ExpandableAdapter(private val context: Context,val itemList:ArrayList<AreaItem>) : Adapter<RecyclerView.ViewHolder>() {

    //RecyclerView.Adapter<ExpandableAdapter.ExpandableViewHolder>(){
    companion object{
        private val VIEW_TYPE_ITEM = 0
        private val VIEW_TYPE_LOADING = 1
    }
    class LoadingViewHolder(private  val binding:LoadingItemBinding) : ViewHolder(binding.root){}
    class ExpandableViewHolder(private val binding: AreaItemRowBinding): ViewHolder(binding.root) {
        fun bind(areaItem: AreaItem, position: Int) {
            binding.areaItemTvName.text = areaItem.name
            binding.areaItemTvDate.text = areaItem.date
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

//    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {
//        holder.bind(itemList[position],position)
//    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ExpandableViewHolder){
            holder.bind(itemList[position],position)
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

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ExpandableViewHolder {
//        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        val binding  = AreaItemRowBinding.inflate(inflater,parent,false)
//        return ExpandableViewHolder(binding)
//    }

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