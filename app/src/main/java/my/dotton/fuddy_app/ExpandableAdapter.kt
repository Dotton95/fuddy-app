package my.dotton.fuddy_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.dotton.fuddy_app.databinding.AreaItemRowBinding
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
class ExpandableAdapter(private val context: Context, private val itemList:List<AreaItem>):RecyclerView.Adapter<ExpandableAdapter.ExpandableViewHolder>() {
    class ExpandableViewHolder(val binding: AreaItemRowBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(areaItem:AreaItem,position:Int,context: Context){
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpandableViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var binding  = AreaItemRowBinding.inflate(inflater,parent,false)
        return ExpandableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {
        holder.bind(itemList[position],position,context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}