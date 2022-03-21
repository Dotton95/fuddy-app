package my.dotton.fuddy_app.Fragment

import android.content.Context
import android.location.Geocoder
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import my.dotton.fuddy_app.*
import my.dotton.fuddy_app.Model.AreaResponse
import my.dotton.fuddy_app.databinding.FragmentAreaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer
import kotlin.math.round

class AreaFragment : BaseFragment<FragmentAreaBinding>(R.layout.fragment_area) {

    private lateinit var areaAdapter:ExpandableAdapter

    companion object{
        var totalCount = 0 //전체 아이템 개수
        var totalPage = 0//총 페이지 수
        var page = 1//현재 페이지
        var limit = 20//검색할 아이템 갯수
        var currentCount=0
        var ctprvnNm = ""
        var spinnerList = arrayListOf<String>(
            "시도를 선택해주세요","서울특별시","경기도","인천광역시","강원도","경상남도","경상북도","전라남도","전라북도","충청남도","충청북도","대전광역시","광주광역시","대구광역시","부산광역시","제주특별자치도"
        )
    }
    override fun initView() {
        super.initView()
        binding.apply {

            areaSpinner.adapter = ArrayAdapter(requireContext(),R.layout.spinner_custom,spinnerList)

            areaRv.visibility = View.GONE
            areaTvNodata.visibility = View.GONE
            areaProgressbar.visibility = View.GONE

            areaRv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                        if(page<totalPage){//다음 페이지가 있을때
                            page++
                            getAreaData(false,ctprvnNm)
                        }
                    }
                }
            })
            areaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(position > 0){
                        //itemList.clear()
                        getAreaData(true,spinnerList[position])
                        ctprvnNm = spinnerList[position]
                        areaProgressbar.visibility = View.VISIBLE
                        areaRv.visibility = View.GONE
                        areaTvNodata.visibility = View.GONE
                        Handler(Looper.getMainLooper()).postDelayed({
                            areaProgressbar.visibility = View.GONE
                            areaRv.visibility = View.VISIBLE
                        },2000)
                    }else{
                        areaRv.visibility = View.GONE
                        areaTvNodata.visibility = View.VISIBLE
                        areaTvNodata.text = "시도를 선택해주세요"
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    areaRv.visibility = View.GONE
                    areaTvNodata.visibility = View.VISIBLE
                    areaTvNodata.text = "시도를 선택해주세요"
                }
            }
        }
    }
    private fun getAreaData(first:Boolean,ctprvnNm:String){
        /*
        areaItem dataList
    type json

prmisnZoneNm 허가구역명
ctprvnNm  시도명
signguNm 시군구명
lnmadr 소재지지번주소
latitude 위도
longitude 경도
vhcleCo 푸드트럭운영대수
primsnZoneRntfee 허가구역 사용료
beginDate 허가구역운영시작일자
endDate 허가구역운영종료일자
rstde 허가구역휴무일
weekdayOperOpenHhmm 허가구역평일운영시작시각
weekdayOperColseHhmm 허가구역평일운영종료시각
wkendOperOpenHhmm 허가구역주말운영시작시각
wkendOperColseHhmm 허가구역주말운영종료시각
lmttPrdlst 판매제한품목
institutionNm 관리기관명
phoneNumber 관리기관 전화번호
referenceDate 데이터 기준일자
 */

        if(first) page = 0

        val myRetrofit = RetrofitClient.areaRetrofit.create(AreaInterface::class.java)
                            .getAreaData(page,limit,"xml",BuildConfig.COVID_API_KEY,ctprvnNm)

        myRetrofit.enqueue(object : Callback<AreaResponse> {
            override fun onResponse(call: Call<AreaResponse>, response: Response<AreaResponse>) {
                if(response.isSuccessful){

                    val result = response.body() as AreaResponse
                    var addItemList = ArrayList<AreaItem>()

                    for(i in result.body.items.item.indices){
                        currentCount++
                        //데이터 UI 세팅
                        var item = AreaItem()
                        item.name = result.body.items.item[i].prmisnZoneNm+""
                        item.date = result.body.items.item[i].beginDate.plus(" ~ ").plus(result.body.items.item[i].endDate)
                        addItemList.add(item)
                    }
                    if(first){
                        //처음검색시 결과값 가져오기
                        totalCount = result.body.totalCount
                        page = result.body.pageNo
                        totalPage = totalCount/limit + 1

                        if(totalPage!=1)  addItemList.add(AreaItem("",""))

                        binding.areaRv.layoutManager = LinearLayoutManager(requireContext())
                        areaAdapter = ExpandableAdapter(requireContext(),addItemList)
                        areaAdapter.notifyDataSetChanged()
                        binding.areaRv.adapter = areaAdapter
                    }else{
                        areaAdapter.deleteLoading()
                        areaAdapter.setList(addItemList)
                        if(page==totalPage)   areaAdapter.deleteLoading()
                        areaAdapter.notifyDataSetChanged()

                    }
                }else{
                    Log.d("AreaFragment","getAreaData - onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AreaResponse>, t: Throwable) {
                Log.d("AreaFragment",t.message?:"통신오류")}
        })
    }
}