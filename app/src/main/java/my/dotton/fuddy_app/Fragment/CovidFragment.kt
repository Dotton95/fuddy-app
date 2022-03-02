package my.dotton.fuddy_app.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import my.dotton.fuddy_app.CovidInterface
import my.dotton.fuddy_app.Model.CovidResponse2
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.RetrofitClient
import my.dotton.fuddy_app.databinding.FragmentCovidBinding
import retrofit2.Call
import retrofit2.Callback

import retrofit2.Response
import retrofit2.create

class CovidFragment : BaseFragment<FragmentCovidBinding>(R.layout.fragment_covid) {
    override fun initView() {
        super.initView()
        binding.apply {
            var spinnerList = arrayListOf<String>(
                "서울","경기","인천","강원","경남","경북","전남","전북","충남","충북","세종","대전","울산","광주","대구","부산","제주"
            )
            binding.covidSpinner.adapter = ArrayAdapter(requireContext(),R.layout.spinner_custom,spinnerList)

            binding.covidSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                }
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

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
                    binding.covidTvDefCnt.text = result.body.items.item[sidoCode].defCnt.plus("명")
                    binding.covidTvInDec.text = result.body.items.item[sidoCode].incDec.plus("명")
                    binding.covidTvOverFlowCnt.text = result.body.items.item[sidoCode].overFlowCnt.plus("명")
                    binding.covidTvLocalOccTitle.text = result.body.items.item[sidoCode].localOccCnt.plus("명")
                    binding.covidTvDeathCnt.text = result.body.items.item[sidoCode].deathCnt.plus("명")
                    binding.covidTvQurRate.text = result.body.items.item[sidoCode].qurRate.plus("명")
                }else{

                }
            }

            override fun onFailure(call: Call<CovidResponse2>, t: Throwable) {
            }
        })

    }



}