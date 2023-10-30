import java.time.Duration
import java.time.LocalDateTime

class SmartHomeController(val timeUtils: TimeUtils) {

    var lastMotionTime: LocalDateTime = LocalDateTime.MIN
        private set(value) { field = value}

    fun actuateLights(motionDetected: Boolean) {
        val time = LocalDateTime.now()

        if (motionDetected) {
            lastMotionTime = time
        }

        val timeOfDay = timeUtils.getTimeOfDay(time)
        if (motionDetected && (timeOfDay == "Evening" || timeOfDay == "Night")) {
            BackyardLightSwitcher.turnOn()
        }
        if (Duration.between(time, lastMotionTime).toMinutes() > 1 || timeOfDay == "Morning" || timeOfDay == "Afternoon") {
            BackyardLightSwitcher.turnOff()
        }
    }

}