package demo.home.local

data class LocalConditions(
    val celsiusTemp: Double,
    val weather: Weather,
    val sunsetTime: String,
    val sunriseTime: String
)

enum class Weather {
    SUNNY, RAINY, CLOUDY, SNOWY
}
