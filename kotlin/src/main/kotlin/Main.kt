fun main() {
    println("This just contains some example classes for testing demos")
}

fun exampleUsage() {
    val smartHomeController = SmartHomeController(TimeUtils())
    smartHomeController.actuateLights(true)
}