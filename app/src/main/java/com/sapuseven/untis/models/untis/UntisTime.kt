package com.sapuseven.untis.models.untis

import com.sapuseven.untis.helpers.DateTimeUtils
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.joda.time.LocalTime

@Serializable(UntisTime.Companion::class)
class UntisTime(
	val time: String
) {
	companion object : KSerializer<UntisTime> {
		override val descriptor: SerialDescriptor =
			PrimitiveSerialDescriptor("UntisTime", PrimitiveKind.STRING)

		override fun serialize(encoder: Encoder, obj: UntisTime) {
			encoder.encodeString(obj.time)
		}

		override fun deserialize(decoder: Decoder): UntisTime {
			return UntisTime(decoder.decodeString())
		}

		fun fromLocalTime(localTime: LocalTime): UntisTime {
			return UntisTime(localTime.toString(DateTimeUtils.tTimeNoSeconds()))
		}
	}

	override fun toString(): String {
		return time
	}

	fun toLocalTime(): LocalTime {
		return DateTimeUtils.tTimeNoSeconds().parseLocalTime(time)
	}
}
