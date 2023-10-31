import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.time.LocalDateTime

class SmartHomeControllerTest {

    @Test
    fun `actuateLights should turn on lights if motion detected at night`() {
        // Arrange
        val smartHomeController = SmartHomeController(TimeUtils())

        // Act
        smartHomeController.actuateLights(true)

        // Assert
    }

}
