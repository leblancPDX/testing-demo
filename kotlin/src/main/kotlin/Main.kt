import tested.home.LocalTimeProvider
import tested.home.SmartHomeController
import tested.home.UserContext
import tested.home.local.LocalConditionsClient
import tested.home.local.LocalConditionsService

fun main() {
    println("This just contains some example classes for testing demos")
}

fun exampleUsage() {
    val userContext = UserContext(37.7749, -122.4194)
    val localConditionsService = LocalConditionsService(userContext, LocalConditionsClient(), LocalTimeProvider())
    val smartHomeController = SmartHomeController(localConditionsService, LocalTimeProvider())
    smartHomeController.actuateLights(true)
}
