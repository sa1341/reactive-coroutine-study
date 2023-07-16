package com.jean.reactivecoroutinestudy.json

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.modules.SerializersModule
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class KotlinJacksonTest {

    @Test
    fun `객체를 json string 타입으로 직렬화한다`() {
        // given
        val person = Person("junyoung", 33)

        // when
        val json = Json.encodeToString(person)

        // then
        println(json)
    }

    @Test
    fun `json string 객체로 직렬화한다`() {
        // given
        val json = Json { ignoreUnknownKeys = true }

        val data = """
            {
                "name":"junyoung"
            }
        """.trimIndent()

        // when
        val person: Person = json.decodeFromString(data)

        // then
        println(person)
    }

    @Test
    fun `LocalDate객체를 json 직렬화 한다`() {
        // given
        val json = Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
            encodeDefaults = true
            serializersModule = SerializersModule {
                contextual(LocalDate::class, LocalDateSerializer)
            }
        }

        // when
        val job = Job(name = "jean", startDate = LocalDate.now())

        val jsonStr = json.encodeToString(job)

        // then
        println(jsonStr)
    }

    @Test
    fun `LocalDate 객체로 역직렬화 한다`() {
        // given
        val json = Json {
            ignoreUnknownKeys = true
            namingStrategy = JsonNamingStrategy.SnakeCase
            encodeDefaults = true
            serializersModule = SerializersModule {
                contextual(LocalDate::class, LocalDateSerializer)
            }
        }

        // when
        val data = """
            {
              "name": "jean",
              "start_date": "20230716"
            }
        """.trimIndent()

        val job = json.decodeFromString<Job>(data)

        // then
        println(job)
    }
}

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)
    private val datePattern: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    override fun serialize(encoder: Encoder, value: LocalDate) {
        val result = value.format(datePattern)
        encoder.encodeString(result)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), datePattern)
    }
}

@Serializable
data class Job(
    val name: String,
    @Contextual
    val startDate: LocalDate? = null
)

@Serializable
data class Person(
    val name: String,
    val age: Int
)
