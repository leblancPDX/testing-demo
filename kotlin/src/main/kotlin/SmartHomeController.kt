import java.time.Duration
import java.time.LocalDateTime

class SmartHomeController(private val timeUtils: TimeUtils,
                          private val timeProvider: TimeProvider) {

    var lastMotionTime: LocalDateTime? = null
        private set(value) { field = value}

    fun actuateLights(motionDetected: Boolean,
                      turnOn: () -> Unit = BackyardLightSwitcher::turnOn,
                      turnOff: () -> Unit = BackyardLightSwitcher::turnOff) {
        val time = timeProvider.getLocalDateTime()

        if (motionDetected) {
            lastMotionTime = time
        }

        val timeOfDay = timeUtils.getTimeOfDay(time)
        if (motionDetected && (timeOfDay == "Evening" || timeOfDay == "Night")) {
            turnOn()
        }
        if (Duration.between(time, lastMotionTime).toMinutes() > 1 || timeOfDay == "Morning" || timeOfDay == "Afternoon") {
            turnOff()
        }
    }

}