package com.sapuseven.untis.models.untis.response

import kotlinx.serialization.Serializable

@Serializable
data class LessonTopicResponse(
		val result: LessonTopicResult? = null
) : BaseResponse()

@Serializable
data class LessonTopicResult(
		val success: Boolean
)
