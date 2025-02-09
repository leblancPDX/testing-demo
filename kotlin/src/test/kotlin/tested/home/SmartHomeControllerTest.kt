package tested.home

import io.mockk.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import tested.home.gadget.BackyardLightGadget
import tested.home.gadget.SprinklerGadget
import tested.home.local.LocalConditionsService
import java.time.LocalDateTime

class SmartHomeControllerTest {

    private val mockLocalConditionsService = mockk<LocalConditionsService>()
    private val mockTimeProvider = mockk<TimeProvider>()

    private val smartHomeController = SmartHomeController(mockLocalConditionsService, mockTimeProvider)

    @Nested
    @DisplayName("when actuateLights is called")
    inner class ActuateLightsTests {
        @Test
        fun `should turn on lights when motion detected at night`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isNight() } returns true
            var lightOn = false

            // Act
            smartHomeController.actuateLights(true, { lightOn = true }, { lightOn = false})

            // Assert
            assertTrue(lightOn)
            assertEquals(time, smartHomeController.lastMotionTime)
        }

        @Test
        fun `should not turn on lights when motion detected during the day`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 12, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isNight() } returns false
            var lightOn = true

            // Act
            smartHomeController.actuateLights(true, { lightOn = true }, { lightOn = false})

            // Assert
            assertFalse(lightOn)
            assertEquals(time, smartHomeController.lastMotionTime)
        }

        @Test
        fun `should turn off lights when no motion detected for more than 1 minute`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time andThen time.plusMinutes(2)
            every { mockLocalConditionsService.isNight() } returns true
            var lightOn = true

            // Act
            smartHomeController.actuateLights(true, { lightOn = true }, { lightOn = false})
            smartHomeController.actuateLights(false, { lightOn = true }, { lightOn = false})

            // Assert
            assertFalse(lightOn)
        }

        @Test
        fun `should turn off lights during the day`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 12, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isNight() } returns false
            var lightOn = true

            // Act
            smartHomeController.actuateLights(true, { lightOn = true }, { lightOn = false})

            // Assert
            assertFalse(lightOn)
        }

        @Nested
        @DisplayName("using mock BackyardLightGadget")
        inner class BackyardLightGadgetTests {
            @Test
            fun `should turn on backyard lights`() {
                // Arrange
                mockkObject(BackyardLightGadget)
                justRun { BackyardLightGadget.turnOn() }
                justRun { BackyardLightGadget.turnOff() }
                val time = LocalDateTime.of(2024, 10, 31, 12, 1)
                every { mockTimeProvider.getLocalDateTime() } returns time
                every { mockLocalConditionsService.isNight() } returns true

                // Act
                smartHomeController.actuateLights(true)

                // Assert
                verify(exactly = 1) { BackyardLightGadget.turnOn() }
            }

            @Test
            fun `should turn off backyard lights`() {
                // Arrange
                mockkObject(BackyardLightGadget)
                justRun { BackyardLightGadget.turnOn() }
                justRun { BackyardLightGadget.turnOff() }
                val time = LocalDateTime.of(2024, 10, 31, 12, 1)
                every { mockTimeProvider.getLocalDateTime() } returns time
                every { mockLocalConditionsService.isNight() } returns true

                // Act
                smartHomeController.actuateLights(false)

                // Assert
                verify(exactly = 0) { BackyardLightGadget.turnOn() }
            }
        }
    }

    @Nested
    @DisplayName("when actuateSprinklers is called")
    inner class ActuateSprinklersTests {
        @Test
        fun `should turn on sprinklers when warm and not raining`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isWarm() } returns true
            every { mockLocalConditionsService.isRaining() } returns false
            var sprinklerOn = false

            // Act
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })

            // Assert
            assertTrue(sprinklerOn)
            assertEquals(time, smartHomeController.sprinklerActivationTime)
        }

        @Test
        fun `should turn off sprinklers when raining`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isWarm() } returns true
            every { mockLocalConditionsService.isRaining() } returns true
            var sprinklerOn = true

            // Act
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })

            // Assert
            assertFalse(sprinklerOn)
        }

        @Test
        fun `should turn off sprinklers when temperature drops below 20 degrees`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isWarm() } returns true andThen false
            every { mockLocalConditionsService.isRaining() } returns false
            var sprinklerOn = true

            // Act
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })

            // Assert
            assertFalse(sprinklerOn)
        }

        @Test
        fun `should turn off sprinklers after 5 minutes`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time andThen time.plusMinutes(6)
            every { mockLocalConditionsService.isWarm() } returns true
            every { mockLocalConditionsService.isRaining() } returns false
            var sprinklerOn = false

            // Act
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })

            // Assert
            assertFalse(sprinklerOn)
        }

        @Test
        fun `should leave sprinkler off when it is cold`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isWarm() } returns false
            var sprinklerOn = false

            // Act
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })

            // Assert
            assertFalse(sprinklerOn)
        }

        @Test
        fun `should leave sprinkler off when it is warm and raining`() {
            // Arrange
            val time = LocalDateTime.of(2024, 10, 31, 5, 1)
            every { mockTimeProvider.getLocalDateTime() } returns time
            every { mockLocalConditionsService.isWarm() } returns true
            every { mockLocalConditionsService.isRaining() } returns true
            var sprinklerOn = false

            // Act
            smartHomeController.actuateSprinklers({ sprinklerOn }, { sprinklerOn = true }, { sprinklerOn = false })

            // Assert
            assertFalse(sprinklerOn)
        }

        @Nested
        @DisplayName("using mock SprinklerGadget")
        inner class SprinklerGadgetTests {
            @Test
            fun `should turn on sprinklers`() {
                // Arrange
                mockkObject(SprinklerGadget)
                every { SprinklerGadget.isOn() } returns false
                justRun { SprinklerGadget.turnOn() }
                justRun { SprinklerGadget.turnOff() }
                val time = LocalDateTime.of(2024, 10, 31, 12, 1)
                every { mockTimeProvider.getLocalDateTime() } returns time
                every { mockLocalConditionsService.isWarm() } returns true
                every { mockLocalConditionsService.isRaining() } returns false

                // Act
                smartHomeController.actuateSprinklers()

                // Assert
                verify(exactly = 1) { SprinklerGadget.turnOn() }
            }

            @Test
            fun `should turn off sprinklers`() {
                // Arrange
                mockkObject(SprinklerGadget)
                every { SprinklerGadget.isOn() } returns true
                justRun { SprinklerGadget.turnOn() }
                justRun { SprinklerGadget.turnOff() }
                val time = LocalDateTime.of(2024, 10, 31, 12, 1)
                every { mockTimeProvider.getLocalDateTime() } returns time
                every { mockLocalConditionsService.isWarm() } returns true
                every { mockLocalConditionsService.isRaining() } returns true

                // Act
                smartHomeController.actuateSprinklers()

                // Assert
                verify(exactly = 1) { SprinklerGadget.turnOff() }
            }
        }
    }

}