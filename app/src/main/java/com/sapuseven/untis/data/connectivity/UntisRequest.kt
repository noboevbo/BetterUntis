package com.sapuseven.untis.data.connectivity

import android.net.Uri
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.coroutines.awaitObjectResponseResult
import com.github.kittinunf.fuel.serialization.kotlinxDeserializerOf
import com.github.kittinunf.result.Result
import com.sapuseven.untis.data.connectivity.UntisApiConstants.DEFAULT_WEBUNTIS_HOST
import com.sapuseven.untis.data.connectivity.UntisApiConstants.DEFAULT_WEBUNTIS_PATH
import com.sapuseven.untis.data.databases.entities.User
import com.sapuseven.untis.helpers.SerializationUtils.getJSON
import com.sapuseven.untis.models.untis.params.BaseParams
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.io.UnsupportedEncodingException
import java.net.URISyntaxException


class UntisRequest {
	suspend inline fun <reified T : Any> request(query: UntisRequestQuery): Result<T, FuelError> {
		// println("HERE")
		val result = Fuel.post(query.getUri().toString())
			.header(mapOf("Content-Type" to "application/json; charset=UTF-8"))
			.body(getJSON().encodeToString(query.data))
			.awaitObjectResponseResult<T>(kotlinxDeserializerOf(getJSON()))
		// println(result.first)
		// println(result.second)
		return result.third
	}

	class UntisRequestQuery(val user: User? = null, apiUrl: String? = null) {
		var url = apiUrl ?: user?.apiUrl ?: user?.schoolId.let {
			"https://$DEFAULT_WEBUNTIS_HOST$DEFAULT_WEBUNTIS_PATH$it"
		}
		var data: UntisRequestData = UntisRequestData()
		var proxyHost: String? = null

		@Throws(URISyntaxException::class, UnsupportedEncodingException::class)
		fun getUri(): Uri {
			return Uri.parse(url).buildUpon().apply {
				if (!proxyHost.isNullOrBlank()) {
					authority(proxyHost)
				}
			}.build()
		}
	}

	@Serializable
	class UntisRequestData {
		var id: String = "-1"
		var jsonrpc: String = "2.0"
		var method: String = ""
		var params: List<BaseParams> = emptyList()
	}
}
