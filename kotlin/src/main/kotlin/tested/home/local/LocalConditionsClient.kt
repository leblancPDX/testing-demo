package tested.home.local


class LocalConditionsClient {

    /**
     * Gets the local conditions from an external service. Uses the user location in the request.
     * @param lat the latitude of the user
     * @param lon the longitude of the user
     * @return the local conditions
     */
    fun getLocalConditions(lat: Double, lon: Double): LocalConditions {
        // Using a fake implementation because this is just for demonstration purposes
        // Normally this would call an external service
        return LocalConditions(25.0, Weather.SUNNY, "18:00", "06:00")
    }

    fun defaultLocalConditions(): LocalConditions {
        return LocalConditions(25.0, Weather.SUNNY, "18:00", "06:00")
    }
}
