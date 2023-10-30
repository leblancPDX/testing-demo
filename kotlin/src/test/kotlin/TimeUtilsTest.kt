import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime

class TimeUtilsTest {

    private val timeUtils = TimeUtils()

    @Test
    fun `getTimeOfDay should return evening if after 6pm`() {
        // Set system time to 18:01?
        //assertEquals("Evening", timeUtils.getTimeOfDay())
        // Set system time back to real time?
    }

    @Test
    fun `getTimeOfDay should return Afternoon if between 1200 and 1800`() {
        val time = LocalDateTime.of(2023, 10, 31, 14, 0)
        val timeOfDay = timeUtils.getTimeOfDay(time)
        assertEquals("Afternoon", timeOfDay)
    }

    @Test
    fun `getTimeOfDay should return Evening if between 1800 and 0000`() {
        val time = LocalDateTime.of(2023, 10, 31, 19, 0)
        val timeOfDay = timeUtils.getTimeOfDay(time)
        assertEquals("Evening", timeOfDay)
    }

    @Test
    fun `getTimeOfDay should return Night if between 0000 and 0600`() {
        val time = LocalDateTime.of(2023, 10, 31, 5, 0)
        val timeOfDay = timeUtils.getTimeOfDay(time)
        assertEquals("Night", timeOfDay)
    }

    @Test
    fun `getTimeOfDay should return Morning if between 0600 and 1200`() {
        val time = LocalDateTime.of(2023, 10, 31, 7, 0)
        val timeOfDay = timeUtils.getTimeOfDay(time)
        assertEquals("Morning", timeOfDay)
    }
}