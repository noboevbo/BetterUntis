package com.sapuseven.untis.helpers

import kotlinx.serialization.json.Json

object SerializationUtils {
	fun getJSON() = Json {
		ignoreUnknownKeys = false
		isLenient = false
		encodeDefaults = true
	}
}
