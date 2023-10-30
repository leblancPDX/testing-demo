import java.time.LocalDateTime

interface TimeProvider {
    fun getLocalDateTime(): LocalDateTime
}

class LocalTimeProvider(): TimeProvider {
    override fun getLocalDateTime(): LocalDateTime {
        return LocalDateTime.now()
    }
}
