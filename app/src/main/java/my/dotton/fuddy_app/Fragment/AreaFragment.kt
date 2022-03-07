package my.dotton.fuddy_app.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.dotton.fuddy_app.*
import my.dotton.fuddy_app.Model.AreaResponse
import my.dotton.fuddy_app.Model.CovidResponse2
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

    override fun initView() {
        super.initView()
        binding.apply {
            val itemList = ArrayList<AreaItem>()
        }
    }
    private fun getAreaData(key:String){
        val areaInterface = RetrofitClient.areaRetrofit.create(AreaInterface::class.java)
        areaInterface.getAreaData("xml",key).enqueue(object : Callback<AreaResponse> {
            override fun onResponse(call: Call<AreaResponse>, response: Response<AreaResponse>) {
                if(response.isSuccessful){
                    val result = response.body() as AreaResponse

                }else{
                    Log.d("AreaFragment","getAreaData - onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AreaResponse>, t: Throwable) {
                Log.d("AreaFragment",t.message?:"통신오류")}
        })
    }
}