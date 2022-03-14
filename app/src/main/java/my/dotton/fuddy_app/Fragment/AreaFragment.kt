package my.dotton.fuddy_app.Fragment

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.dotton.fuddy_app.*
import my.dotton.fuddy_app.Model.AreaResponse
import my.dotton.fuddy_app.databinding.FragmentAreaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AreaFragment : BaseFragment<FragmentAreaBinding>(R.layout.fragment_area) {

//    type json
//
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

    private lateinit var areaAdapter:ExpandableAdapter


    
    private var totalCount = 0 //전체 아이템 개수
    private var isNext = false //다음 페이지 유무
    private var totalPage = 0//총 페이지 수
    private var page = 0 //현재 페이지
    private var limit = 10 //한 번에 가져올 아이템 수

    override fun initView() {
        super.initView()
        binding.apply {
            getAreaData(BuildConfig.COVID_API_KEY)
            /*binding.areaRv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //화면에 보이는 마지막 아이템의 포지션
                    val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                    val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                    //스크롤이 끝에 도달했다면
                    if(lastVisibleItemPosition == itemTotalCount){
                        loadMore()
                    }
                }
            })*/
        }
    }
    private fun getAreaData(key:String){
        val areaInterface = RetrofitClient.areaRetrofit.create(AreaInterface::class.java)
        areaInterface.getAreaData(page,limit,"xml",key,"서울특별시").enqueue(object : Callback<AreaResponse> {
            override fun onResponse(call: Call<AreaResponse>, response: Response<AreaResponse>) {
                if(response.isSuccessful){
                    val result = response.body() as AreaResponse
                    var itemList = ArrayList<AreaItem>()
                    totalCount = result.body.totalCount
                    page = result.body.pageNo
                    totalPage = totalCount/limit + 1

                    for(i in 0 until 10){
                        var item = AreaItem()
                        item.name = result.body.items.item[i].prmisnZoneNm+""
                        item.date = result.body.items.item[i].beginDate.plus(" ~ ").plus(result.body.items.item[i].endDate)
                        itemList.add(item)
                    }
                    Log.d("dotton",itemList[0].name)
                    binding.areaRv.layoutManager = LinearLayoutManager(requireContext())
                    areaAdapter = ExpandableAdapter(requireContext(),itemList)
                    binding.areaRv.adapter = areaAdapter

                }else{
                    Log.d("AreaFragment","getAreaData - onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AreaResponse>, t: Throwable) {
                Log.d("AreaFragment",t.message?:"통신오류")}
        })
    }
//    fun loadMore(){
//        itemList.add(AreaItem("",""))
//        areaAdapter.notifyItemInserted(itemList.size - 1)
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            itemList.removeAt(itemList.size - 1)
//            var scrollPosition = itemList.size
//            areaAdapter.notifyItemRemoved(scrollPosition)
//            page++
//            getAreaData(BuildConfig.COVID_API_KEY)
//            areaAdapter.notifyDataSetChanged()
//        },1000)
//    }
}