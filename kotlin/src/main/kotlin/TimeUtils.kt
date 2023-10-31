import java.time.LocalDateTime

class TimeUtils {

    fun getTimeOfDay(): String {
        val time = LocalDateTime.now()
        if (time.hour in 0..5) {
            return "Night"
        }
        if (time.hour in 6..11) {
            return "Morning"
        }
        if (time.hour in 12..17) {
            return "Afternoon"
        }
        return "Evening"
    }

}