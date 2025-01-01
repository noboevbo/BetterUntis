package com.sapuseven.untis.helpers

import com.sapuseven.untis.BuildConfig
import kotlinx.serialization.json.Json

object SerializationUtils {
	fun getJSON() = Json {
		ignoreUnknownKeys = true
		isLenient = true
		encodeDefaults = true
	}
}
