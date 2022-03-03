package my.dotton.fuddy_app.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import my.dotton.fuddy_app.BuildConfig
import my.dotton.fuddy_app.CovidInterface
import my.dotton.fuddy_app.Model.CovidResponse2
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.RetrofitClient
import my.dotton.fuddy_app.databinding.FragmentCovidBinding
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response
import retrofit2.create
import java.text.DecimalFormat

class CovidFragment : BaseFragment<FragmentCovidBinding>(R.layout.fragment_covid) {

    lateinit var fadeInAnim:Animation
    lateinit var fadeInAnim2:Animation
    lateinit var fadeInAnim3:Animation
    lateinit var fadeInAnim4:Animation
    lateinit var fadeInAnim5:Animation
    lateinit var fadeInAnim6:Animation


    override fun initView() {
        super.initView()
        binding.apply {
            var spinnerList = arrayListOf<String>(
                "서울","경기","인천","강원","경남","경북","전남","전북","충남","충북","세종","대전","울산","광주","대구","부산","제주"
            )
            binding.covidSpinner.adapter = ArrayAdapter(requireContext(),R.layout.spinner_custom,spinnerList)






            binding.covidSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    getSidoCovid(BuildConfig.COVID_API_KEY,spinnerList[position])
                }
                override fun onNothingSelected(parent: AdapterView<*>?) { getSidoCovid(BuildConfig.COVID_API_KEY,"서울") }
            }
        }
    }

    private fun getSidoCovid(key:String,sido:String){
        val covidInterface = RetrofitClient.covidRetrofit.create(CovidInterface::class.java)

        val sidoCode = when(sido){
            // "검역" -> 0
            "제주" -> 1
            "경남" -> 2
            "경북" -> 3
            "전남" -> 4
            "전북" -> 5
            "충남" -> 6
            "충북" -> 7
            "강원" -> 8
            "경기" -> 9
            "세종" -> 10
            "울산" -> 11
            "대전" -> 12
            "광주" -> 13
            "인천" -> 14
            "대구" -> 15
            "부산" -> 16
            "서울" -> 17
            else -> 18
            //else -> 합계
        }

        covidInterface.getSidoCovid(key).enqueue(object : Callback<CovidResponse2> {
            override fun onResponse(call: Call<CovidResponse2>, response: Response<CovidResponse2>) {
                if(response.isSuccessful){
                    val result = response.body() as CovidResponse2

                    binding.covidTvDefCnt.text = decimalFormat(result.body.items.item[sidoCode].defCnt).plus("명")
                    binding.covidTvInDec.text = decimalFormat(result.body.items.item[sidoCode].incDec).plus("명")
                    binding.covidTvOverFlowCnt.text = decimalFormat(result.body.items.item[sidoCode].overFlowCnt).plus("명")
                    binding.covidTvLocalOccCnt.text = decimalFormat(result.body.items.item[sidoCode].localOccCnt).plus("명")
                    binding.covidTvDeathCnt.text = decimalFormat(result.body.items.item[sidoCode].deathCnt).plus("명")
                    binding.covidTvQurRate.text = decimalFormat(result.body.items.item[sidoCode].qurRate).plus("명")

                    viewAnim()
                }else{
                    Log.d("CovidFragment","getSidoCovid - onResponse : Error code ${response.code()}")
                }
            }
            override fun onFailure(call: Call<CovidResponse2>, t: Throwable) {Log.d("CovidFragment",t.message?:"통신오류")}
        })
    }

    private fun decimalFormat(input:String) : String{
        return DecimalFormat("#,###").format(input.toInt())
    }
    private fun viewAnim(){
        fadeInAnim = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in)
        fadeInAnim2 = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in2)
        fadeInAnim3 = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in3)
        fadeInAnim4 = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in4)
        fadeInAnim5 = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in5)
        fadeInAnim6 = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in6)

        binding.covidTvQurRateTitle.visibility = View.VISIBLE
        binding.covidTvInDecTitle.visibility = View.VISIBLE
        binding.covidTvOverFlowCntTitle.visibility = View.VISIBLE
        binding.covidTvLocalOccTitle.visibility = View.VISIBLE
        binding.covidTvDeathCntTitle.visibility = View.VISIBLE
        binding.covidTvQurRateTitle.visibility = View.VISIBLE


        binding.covidTvDefCntTitle.startAnimation(fadeInAnim)
        binding.covidTvDefCnt.startAnimation(fadeInAnim)
        binding.covidTvInDecTitle.startAnimation(fadeInAnim2)
        binding.covidTvInDec.startAnimation(fadeInAnim2)
        binding.covidTvOverFlowCntTitle.startAnimation(fadeInAnim3)
        binding.covidTvOverFlowCnt.startAnimation(fadeInAnim3)
        binding.covidTvLocalOccTitle.startAnimation(fadeInAnim4)
        binding.covidTvLocalOccCnt.startAnimation(fadeInAnim4)
        binding.covidTvDeathCntTitle.startAnimation(fadeInAnim5)
        binding.covidTvDeathCnt.startAnimation(fadeInAnim5)
        binding.covidTvQurRateTitle.startAnimation(fadeInAnim6)
        binding.covidTvQurRate.startAnimation(fadeInAnim6)
    }
}