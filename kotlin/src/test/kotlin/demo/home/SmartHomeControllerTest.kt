package demo.home

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tested.home.local.LocalConditionsService

class SmartHomeControllerTest {

    @Nested
    @DisplayName("when actuateLights is called")
    inner class ActuateLightsTests {
        @Test
        fun `should turn on the lights`() {

        }
    }

    @Nested
    @DisplayName("when actuateSprinklers is called")
    inner class ActuateSprinklersTests {
        @Test
        fun `should turn on the sprinklers`() {

        }
    }
}
