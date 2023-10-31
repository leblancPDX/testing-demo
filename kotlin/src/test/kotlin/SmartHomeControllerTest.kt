import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime

class SmartHomeControllerTest {

    private var turnOnCount = 0
    private var turnOffCount = 0

    @BeforeEach
    fun setup() {
        turnOnCount = 0
        turnOffCount = 0
    }

    @Test
    fun `actuateLights should turn on lights if motion detected at night`() {
        // Arrange
        val time = LocalDateTime.of(2023, 10, 31, 5, 1)
        val smartHomeController = SmartHomeController(TimeUtils(), FakeTimeProvider(time))
        var turnedOn = false
        val turnOnAction = { turnedOn = true }
        val turnOffAction = { turnedOn = false }

        // Act
        smartHomeController.actuateLights(true, turnOnAction, turnOffAction)

        // Assert
        assertTrue(turnedOn)
        assertEquals(time, smartHomeController.lastMotionTime)
    }

}

class FakeTimeProvider(private var localDateTime: LocalDateTime): TimeProvider {
    override fun getLocalDateTime(): LocalDateTime {
        return localDateTime
    }
}