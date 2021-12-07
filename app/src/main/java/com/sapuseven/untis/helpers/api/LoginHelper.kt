package com.sapuseven.untis.helpers.api

import com.sapuseven.untis.R
import com.sapuseven.untis.data.connectivity.UntisApiConstants
import com.sapuseven.untis.data.connectivity.UntisAuthentication
import com.sapuseven.untis.data.connectivity.UntisRequest
import com.sapuseven.untis.helpers.ErrorMessageDictionary
import com.sapuseven.untis.helpers.SerializationUtils
import com.sapuseven.untis.models.UntisSchoolInfo
import com.sapuseven.untis.models.untis.params.AppSharedSecretParams
import com.sapuseven.untis.models.untis.params.SchoolSearchParams
import com.sapuseven.untis.models.untis.params.UserDataParams
import com.sapuseven.untis.models.untis.response.AppSharedSecretResponse
import com.sapuseven.untis.models.untis.response.SchoolSearchResponse
import com.sapuseven.untis.models.untis.response.UserDataResponse
import com.sapuseven.untis.models.untis.response.UserDataResult
import kotlinx.serialization.decodeFromString
import java.net.UnknownHostException

class LoginHelper(val loginData: LoginDataInfo, val onStatusUpdate: (statusStringRes: Int) -> Unit, val onError: (error: LoginErrorInfo) -> Unit) {
	private val api: UntisRequest = UntisRequest()
	private val proxyHost: String? = null // TODO: Pass proxy host

	init {
		onStatusUpdate(R.string.logindatainput_connecting)
	}

	suspend fun loadSchoolInfo(schoolId: Int): UntisSchoolInfo? {
		onStatusUpdate(R.string.logindatainput_aquiring_schoolid)

		val query = UntisRequest.UntisRequestQuery()

		query.data.method = UntisApiConstants.METHOD_SEARCH_SCHOOLS
		query.url = UntisApiConstants.SCHOOL_SEARCH_URL
		query.proxyHost = proxyHost
		query.data.params = listOf(SchoolSearchParams(schoolid = schoolId))

		val result = api.request(query)
		result.fold({ data ->
			val untisResponse = SerializationUtils.getJSON().decodeFromString<SchoolSearchResponse>(data)

			untisResponse.result?.let {
				if (it.schools.size != 1)
					onError(LoginErrorInfo(errorMessageStringRes = R.string.logindatainput_error_invalid_school))
				else
					return it.schools[0]
			} ?: run {
				onError(LoginErrorInfo(errorCode = untisResponse.error?.code, errorMessage = untisResponse.error?.message))
			}
		}, { error ->
			onError(LoginErrorInfo(errorMessageStringRes = R.string.logindatainput_error_generic, errorMessage = error.message))
		})

		return null
	}

	suspend fun loadAppSharedSecret(apiUrl: String): String? {
		onStatusUpdate(R.string.logindatainput_aquiring_app_secret)

		val query = UntisRequest.UntisRequestQuery()

		query.url = apiUrl
		query.proxyHost = proxyHost
		query.data.method = UntisApiConstants.METHOD_GET_APP_SHARED_SECRET
		query.data.params = listOf(AppSharedSecretParams(loginData.user, loginData.password))

		val appSharedSecretResult = api.request(query)

		appSharedSecretResult.fold({ data ->
			val untisResponse = SerializationUtils.getJSON().decodeFromString<AppSharedSecretResponse>(data)

			if (untisResponse.error?.code == ErrorMessageDictionary.ERROR_CODE_INVALID_CREDENTIALS)
				return loginData.password
			if (untisResponse.result.isNullOrEmpty())
				onError(LoginErrorInfo(errorCode = untisResponse.error?.code, errorMessage = untisResponse.error?.message))
			else
				return untisResponse.result
		}, { error ->
			when (error.exception) {
				is UnknownHostException -> onError(LoginErrorInfo(errorCode = ErrorMessageDictionary.ERROR_CODE_NO_SERVER_FOUND))
				else -> onError(LoginErrorInfo(errorMessageStringRes = R.string.logindatainput_error_generic, errorMessage = error.message))
			}
		})

		return null
	}

	suspend fun loadUserData(apiUrl: String, key: String?): UserDataResult? {
		onStatusUpdate(R.string.logindatainput_loading_user_data)

		val query = UntisRequest.UntisRequestQuery()

		query.url = apiUrl
		query.proxyHost = proxyHost
		query.data.method = UntisApiConstants.METHOD_GET_USER_DATA

		if (loginData.anonymous)
			query.data.params = listOf(UserDataParams(auth = UntisAuthentication.createAuthObject()))
		else {
			if (key == null) return null
			query.data.params = listOf(UserDataParams(auth = UntisAuthentication.createAuthObject(loginData.user, key)))
		}

		val userDataResult = api.request(query)

		userDataResult.fold({ data ->
			val untisResponse = SerializationUtils.getJSON().decodeFromString<UserDataResponse>(data) // TODO: Catch json parsing errors if response isn't valid json

			if (untisResponse.result != null) {
				return untisResponse.result
			} else {
				onError(LoginErrorInfo(errorCode = untisResponse.error?.code, errorMessage = untisResponse.error?.message))
			}
		}, { error ->
			onError(LoginErrorInfo(errorMessageStringRes = R.string.logindatainput_error_generic, errorMessage = error.message))
		})

		return null
	}
}