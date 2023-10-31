import java.time.Duration
import java.time.LocalDateTime

class SmartHomeController(private val timeUtils: TimeUtils) {

    var lastMotionTime: LocalDateTime? = null
        private set

    fun actuateLights(motionDetected: Boolean) {
        val time = LocalDateTime.now()

        if (motionDetected) {
            lastMotionTime = time
        }

        val timeOfDay = timeUtils.getTimeOfDay()
        if (motionDetected && (timeOfDay == "Evening" || timeOfDay == "Night")) {
            BackyardLightSwitcher.turnOn()
        }
        val minutesBetweenMotion = Duration.between(time, lastMotionTime).toMinutes()
        if (minutesBetweenMotion > 1 || timeOfDay == "Morning" || timeOfDay == "Afternoon") {
            BackyardLightSwitcher.turnOff()
        }
    }
}