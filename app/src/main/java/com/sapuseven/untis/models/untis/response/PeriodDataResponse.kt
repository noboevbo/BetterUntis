package com.sapuseven.untis.models.untis.response

import com.sapuseven.untis.models.UnknownObject
import com.sapuseven.untis.models.UntisAbsence
import com.sapuseven.untis.models.UntisHomework
import com.sapuseven.untis.models.untis.UntisDate
import com.sapuseven.untis.models.untis.UntisTopic
import kotlinx.serialization.Serializable

@Serializable
data class PeriodDataResponse(
	val result: PeriodDataResult? = null
) : BaseResponse()

@Serializable
data class PeriodDataResult(
	val referencedStudents: List<UntisStudent>,
	val dataByTTId: Map<String, UntisPeriodData>
)

@Serializable
//@Parcelize // No idea why this is needed, but otherwise it crashes the app on pause/resume
data class UntisStudent(
	val id: Int,
	val firstName: String,
	val lastName: String,
	val klasseId: Int? = 0,
	val birthDate: UntisDate? = null,
	val studentOfAge: Boolean? = false,
	val imageUrl: String? = null,
	val medicalCertSince: String? = null,
) {
	fun fullName(): String = "$firstName $lastName"
}

@Serializable
data class UntisPeriodData(
	val ttId: Int,
	val absenceChecked: Boolean,
	val studentIds: List<Int>? = null,
	val absences: List<UntisAbsence>? = null,
	val classRegEvents: List<UnknownObject>? = null,
	val exemptions: List<UnknownObject>? = null,
	val prioritizedAttendances: List<UnknownObject>? = null,
	val text: UnknownObject? = null,
	val topic: UntisTopic?,
	val homeWorks: List<UntisHomework>? = null,
	val seatingPlan: UnknownObject? = null,
	val classRoles: List<UnknownObject>? = null,
	val channel: UnknownObject? = null,
	val can: List<String>,
	val manualNotificationStatus: String? = null
)
