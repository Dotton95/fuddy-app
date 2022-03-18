package my.dotton.fuddy_app.Fragment

import android.content.Context
import android.location.Geocoder
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
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
import kotlin.math.round

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
    private var totalPage = 0//총 페이지 수
    private var page = 0//현재 페이지
    private var limit = 20 //한 번에 가져올 아이템 수

    var itemList = ArrayList<AreaItem>()

    var ctprvnNm = ""
    var signguNm = ""

    override fun initView() {
        super.initView()
        binding.apply {

            areaRv.visibility = View.GONE
            areaTvNodata.visibility = View.GONE

            areaRv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    //화면에 보이는 마지막 아이템의 포지션
                    if(!areaRv.canScrollVertically(1)){
                        areaAdapter.deleteLoading()
                        getAreaData(false,ctprvnNm,signguNm)
//                        if(page<totalPage){
//                            page++
//
//                        }
                    }
                }
            })

            areaSearchview.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(areaSearchview.windowToken,0)

                    areaRv.visibility = View.VISIBLE
                    areaTvNodata.visibility = View.GONE

                    val spaceCheck = query.contains(" ")

                    try {
                        var address = Geocoder(requireContext()).getFromLocationName(query,0)

                        ctprvnNm = address[0].adminArea
                        signguNm = if(address[0].locality!=null) address[0].locality else ""

                        Log.d("dotton",ctprvnNm)
                        Log.d("dotton",signguNm)

                        if(spaceCheck && signguNm == ""){
                            throw Exception()
                        }else{
                            getAreaData(true,ctprvnNm,signguNm)
                        }

                    }catch (e: Exception) {
                        itemList = ArrayList<AreaItem>()
                        areaRv.visibility = View.GONE
                        areaTvNodata.visibility = View.VISIBLE
                        areaTvNodata.text = "주소가 잘못되었습니다"
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    itemList = ArrayList<AreaItem>()
                    areaRv.visibility = View.GONE
                    areaTvNodata.visibility = View.GONE
                    return true
                }
            })
        }
    }
    private fun getAreaData(first:Boolean,ctprvnNm:String, signguNm:String){
        val areaInterface = RetrofitClient.areaRetrofit.create(AreaInterface::class.java)

        val myRetrofit = if(signguNm != "") areaInterface.getAreaDataSigngu(page,limit,"xml",BuildConfig.COVID_API_KEY,ctprvnNm,signguNm)
                         else areaInterface.getAreaData(page,limit,"xml",BuildConfig.COVID_API_KEY,ctprvnNm)

        myRetrofit.enqueue(object : Callback<AreaResponse> {
            override fun onResponse(call: Call<AreaResponse>, response: Response<AreaResponse>) {
                if(response.isSuccessful){
                    val result = response.body() as AreaResponse
                    totalCount = result.body.totalCount
                    page = result.body.pageNo
                    totalPage = totalCount/limit + 1
                    for(i in result.body.items.item.indices){
                        var item = AreaItem()
                        item.name = result.body.items.item[i].prmisnZoneNm+""
                        item.date = result.body.items.item[i].beginDate.plus(" ~ ").plus(result.body.items.item[i].endDate)
                        itemList.add(item)
                    }
                    if(first){
                        var item = AreaItem()
                        binding.areaRv.layoutManager = LinearLayoutManager(requireContext())
                        areaAdapter = ExpandableAdapter(requireContext(),itemList)
                        //areaAdapter.setList(itemList)
                        areaAdapter.notifyItemRangeInserted((page-1)*10,result.body.items.item.size)
                        binding.areaRv.adapter = areaAdapter
                    }else{
                        areaAdapter.setList(itemList)
                        areaAdapter.notifyItemRangeInserted((page-1)*10,result.body.items.item.size)
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