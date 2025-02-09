package demo.home

import tested.home.gadget.BackyardLightGadget
import tested.home.gadget.SprinklerGadget
import tested.home.local.LocalConditionsService
import java.time.Duration
import java.time.LocalDateTime

class SmartHomeController(
    private val localConditionsService: LocalConditionsService
) {
    var lastMotionTime: LocalDateTime? = null
        private set

    var sprinklerActivationTime: LocalDateTime? = null
        private set

    /**
     * Activate the lights if motion is detected at night, and turn them off if no motion is detected for more
     * than 1 minute, or it's daytime.
     */
    fun actuateLights(
        motionDetected: Boolean
    ) {
        val time = LocalDateTime.now()

        if (motionDetected) {
            lastMotionTime = time
            if (localConditionsService.isNight()) {
                BackyardLightGadget.turnOn()
            }
        }

        val minutesSinceLastMotion = minutesBetweenTimes(lastMotionTime, time)
        if (minutesSinceLastMotion > 1 || !localConditionsService.isNight()) {
            BackyardLightGadget.turnOff()
        }
    }

    /**
     * Activate the sprinklers if it is warm and not raining. The sprinklers should be turned off if it starts raining.
     * The sprinklers should remain active until it stops raining and the temperature drops below 20 degrees. The
     * system should remain active for 5 minutes after it is first activated.
     */
    fun actuateSprinklers() {
        if (SprinklerGadget.isOn()) {
            val activeMinutes = minutesBetweenTimes(sprinklerActivationTime, LocalDateTime.now())
            if (localConditionsService.isRaining() || !localConditionsService.isWarm() || activeMinutes > 5) {
                SprinklerGadget.turnOff()
            }
        } else if (localConditionsService.isWarm() && !localConditionsService.isRaining()) {
            sprinklerActivationTime = LocalDateTime.now()
            SprinklerGadget.turnOff()
        }
    }

    private fun minutesBetweenTimes(start: LocalDateTime?, end: LocalDateTime): Long {
        if (start == null) {
            return 0
        }
        return Duration.between(start, end).toMinutes()
    }
}
