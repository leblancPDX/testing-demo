package demo.home.local

import demo.home.TimeProvider
import demo.home.UserContext
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class LocalConditionsServiceTest {

    private val userContext = UserContext(0.0, 0.0)

    private val localConditionsService = LocalConditionsService(userContext, LocalConditionsClient())

    @Nested
    @DisplayName("when isNight is called")
    inner class IsNightTest {
        @Test
        fun `should return true when it is night`() {
            assertTrue(localConditionsService.isNight())
        }
    }

    @Nested
    @DisplayName("when isRaining is called")
    inner class IsRainingTest {
        @Test
        fun `should return true when it is raining`() {
            assertTrue(localConditionsService.isRaining())
        }
    }

    @Nested
    @DisplayName("when isWarm is called")
    inner class IsWarmTest {
        @Test
        fun `should return true when it is warm`() {
            assertTrue(localConditionsService.isWarm())
        }
    }
}
