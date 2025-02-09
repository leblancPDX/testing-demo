package tested.home.local

import tested.home.TimeProvider
import tested.home.UserContext
import java.time.Duration
import java.time.LocalDateTime

private const val REFRESH_INTERVAL_MINUTES = 10

class LocalConditionsService(
    private val userContext: UserContext,
    private val localConditionsClient: LocalConditionsClient,
    private val timeProvider: TimeProvider
) {

    private var localConditions: LocalConditions = localConditionsClient.defaultLocalConditions()
    private var lastUpdated: LocalDateTime = LocalDateTime.MIN

    fun isNight(): Boolean {
        refreshLocalConditions()
        val currentLocalTime = timeProvider.getLocalDateTime()
        return currentLocalTime.isAfter(timeToLocalDateTime(localConditions.sunsetTime)) ||
                currentLocalTime.isBefore(timeToLocalDateTime(localConditions.sunriseTime))
    }

    fun isRaining(): Boolean {
        refreshLocalConditions()
        return localConditions.weather == Weather.RAINY
    }

    fun isWarm(): Boolean {
        refreshLocalConditions()
        return localConditions.celsiusTemp > 20
    }

    private fun refreshLocalConditions() {
        if (Duration.between(lastUpdated, timeProvider.getLocalDateTime()).toMinutes() < REFRESH_INTERVAL_MINUTES) {
            return
        }
        localConditions = localConditionsClient.getLocalConditions(userContext.lat, userContext.lon)
        lastUpdated = timeProvider.getLocalDateTime()
    }

    private fun timeToLocalDateTime(time: String): LocalDateTime {
        val today = timeProvider.getLocalDateTime().toLocalDate()
        val dateTime = today.toString() + "T" + time
        return LocalDateTime.parse(dateTime)
    }
}
