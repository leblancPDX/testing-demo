package tested.home.local

import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tested.home.TimeProvider
import tested.home.UserContext
import java.time.LocalDateTime

class LocalConditionsServiceTest {

    private val mockUserContext = mockk<UserContext>()
    private val mockLocalConditionsClient = spyk<LocalConditionsClient>()
    private val mockTimeProvider = mockk<TimeProvider>()

    private val localConditionsService = LocalConditionsService(mockUserContext, mockLocalConditionsClient, mockTimeProvider)

    @BeforeEach
    fun setUp() {
        // Default mock behavior
        every { mockUserContext.lat } returns 0.0
        every { mockUserContext.lon } returns 0.0
        every { mockTimeProvider.getLocalDateTime() } returns LocalDateTime.now()
    }

    @Nested
    @DisplayName("when isNight is called")
    inner class IsNightTest {
        @Test
        fun `should return true when current time is after sunset and before sunrise`() {
            val currentLocalTime = "2021-06-01T19:00"
            val localConditions = LocalConditions(25.0, Weather.SUNNY, "18:00", "06:00")
            every { mockTimeProvider.getLocalDateTime() } returns LocalDateTime.parse(currentLocalTime)
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            assertTrue(localConditionsService.isNight())
        }

        @Test
        fun `should return false when current time is before sunset`() {
            val currentLocalTime = "2021-06-01T11:00"
            val localConditions = LocalConditions(25.0, Weather.SUNNY, "18:00", "06:00")
            every { mockTimeProvider.getLocalDateTime() } returns LocalDateTime.parse(currentLocalTime)
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            assertFalse(localConditionsService.isNight())
        }

        @Test
        fun `should refresh local conditions if called after more than a 10 minute delay`() {
            val currentLocalTime = "2021-06-01T19:00"
            val time = LocalDateTime.parse(currentLocalTime)
            val localConditions = LocalConditions(25.0, Weather.SUNNY, "18:00", "06:00")
            every { mockTimeProvider.getLocalDateTime() }.returnsMany(time, time, time.plusMinutes(11))
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            localConditionsService.isNight()
            localConditionsService.isNight()
            localConditionsService.isNight()

            verify(exactly = 2) { mockLocalConditionsClient.getLocalConditions(any(), any()) }
        }
    }

    @Nested
    @DisplayName("when isRaining is called")
    inner class IsRainingTest {
        @Test
        fun `should return true when weather is rainy`() {
            val localConditions = LocalConditions(25.0, Weather.RAINY, "18:00", "06:00")
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            assertTrue(localConditionsService.isRaining())
        }

        @Test
        fun `should return false when weather is not rainy`() {
            val localConditions = LocalConditions(25.0, Weather.SNOWY, "18:00", "06:00")
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            assertFalse(localConditionsService.isRaining())
        }
    }

    @Nested
    @DisplayName("when isWarm is called")
    inner class IsWarmTest {
        @Test
        fun `should return true when temperature is above 20 degrees`() {
            val localConditions = LocalConditions(25.0, Weather.SUNNY, "18:00", "06:00")
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            assertTrue(localConditionsService.isWarm())
        }

        @Test
        fun `should return false when temperature is 20 degrees or below`() {
            val localConditions = LocalConditions(20.0, Weather.SUNNY, "18:00", "06:00")
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            assertFalse(localConditionsService.isWarm())
        }

        @Test
        fun `should return false when temperature is negative`() {
            val localConditions = LocalConditions(-5.0, Weather.SUNNY, "18:00", "06:00")
            every { mockLocalConditionsClient.defaultLocalConditions() } returns localConditions
            every { mockLocalConditionsClient.getLocalConditions(any(), any()) } returns localConditions

            assertFalse(localConditionsService.isWarm())
        }
    }
}
