package com.sapuseven.untis.models.untis.params

import com.sapuseven.untis.models.untis.UntisAuth
import kotlinx.serialization.Serializable

@Serializable
data class AbsencesCheckedParams(
	val ttIds: List<Int>,
	val auth: UntisAuth
) : BaseParams()
