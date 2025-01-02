package com.sapuseven.untis.utils

import com.sapuseven.untis.data.connectivity.UntisApiConstants
import com.sapuseven.untis.data.connectivity.UntisAuthentication
import com.sapuseven.untis.data.connectivity.UntisRequest
import com.sapuseven.untis.helpers.SerializationUtils
import com.sapuseven.untis.models.untis.UntisMasterData
import com.sapuseven.untis.models.untis.params.UserDataParams
import com.sapuseven.untis.models.untis.response.UserDataResponse
import kotlinx.serialization.SerializationException
import org.junit.Assert

suspend fun loadMasterData(apiUrl: String): UntisMasterData? {
	val api = UntisRequest()
	val query = UntisRequest.UntisRequestQuery().apply {
		url = apiUrl
		data.method = UntisApiConstants.METHOD_GET_USER_DATA
		data.params = listOf(UserDataParams(auth = UntisAuthentication.createAuthObject()))
	}

	return null
}
