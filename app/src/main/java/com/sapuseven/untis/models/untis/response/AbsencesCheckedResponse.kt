package com.sapuseven.untis.models.untis.response

import kotlinx.serialization.Serializable

@Serializable
data class AbsencesCheckedResponse(
	val result: AbsencesCheckedResult? = null
) : BaseResponse()

@Serializable
data class AbsencesCheckedResult(
	val success: Boolean? = null
)
