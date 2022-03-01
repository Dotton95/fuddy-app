package my.dotton.fuddy_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.dotton.fuddy_app.Fragment.WeatherFragment
import my.dotton.fuddy_app.databinding.WeatherchartItemBinding
import java.text.FieldPosition

class WeatherChartAdapter(private val context:Context, val itemList:ArrayList<WeatherFragment.Item>) : RecyclerView.Adapter<WeatherChartAdapter.WeatherChartViewHolder>() {
    inner class WeatherChartViewHolder(val binding:WeatherchartItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: WeatherFragment.Item,position:Int,context: Context){
            when(item.weather){
                in 200..200 -> binding.weatherchartIv.setImageResource(R.drawable.thunder)
                in 300..599 -> binding.weatherchartIv.setImageResource(R.drawable.rain)
                in 600..699 -> binding.weatherchartIv.setImageResource(R.drawable.snow)
                in 700..799 -> binding.weatherchartIv.setImageResource(R.drawable.mist)
                800 -> binding.weatherchartIv.setImageResource(R.drawable.sun_small)
                801 -> binding.weatherchartIv.setImageResource(R.drawable.cloud_small)
                in 802..899 -> binding.weatherchartIv.setImageResource(R.drawable.cloud_many)
            }
            binding.weatherchartTvTemp.text = item.temp
            binding.weatherchartTvTime.text = item.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherChartViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var binding  = WeatherchartItemBinding.inflate(inflater,parent,false)
        return WeatherChartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherChartViewHolder, position: Int) {holder.bind(itemList[position],position,context)}

    override fun getItemCount(): Int {return itemList.size }
}