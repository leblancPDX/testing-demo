import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class TimeUtilsTest {

    private val timeUtils = TimeUtils()

    @Test
    fun `getTimeOfDay should return evening if after 6pm`() {
        // Set system time to 18:01?
        assertEquals("Evening", timeUtils.getTimeOfDay())
        // Set system time back to real time?
    }
}