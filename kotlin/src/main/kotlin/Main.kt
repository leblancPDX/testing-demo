fun main() {
    println("This just contains some example classes for testing demos")
}

fun exampleUsage() {
    val smartHomeController = SmartHomeController(TimeUtils(),LocalTimeProvider())
    smartHomeController.actuateLights(true, BackyardLightSwitcher::turnOn, BackyardLightSwitcher::turnOff)
}