import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


data class WeatherResult (

	@SerializedName("lat") val lat : Double,
	@SerializedName("lon") val lon : Double,
	@SerializedName("timezone") val timezone : String,
	@SerializedName("timezone_offset") val timezone_offset : Int,
	@SerializedName("current") val current : Current,
	@SerializedName("minutely") val minutely : List<Minutely>,
	@SerializedName("hourly") val hourly : List<Hourly>,
	@SerializedName("daily") val daily : List<Daily>
){
	fun toJsonString(): String?{
		return Gson().toJson(this)
	}
}