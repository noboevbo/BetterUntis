package com.sapuseven.untis.models.untis

import com.sapuseven.untis.models.untis.masterdata.AbsenceReason
import com.sapuseven.untis.models.untis.masterdata.Department
import com.sapuseven.untis.models.untis.masterdata.Duty
import com.sapuseven.untis.models.untis.masterdata.EventReason
import com.sapuseven.untis.models.untis.masterdata.EventReasonGroup
import com.sapuseven.untis.models.untis.masterdata.ExcuseStatus
import com.sapuseven.untis.models.untis.masterdata.Holiday
import com.sapuseven.untis.models.untis.masterdata.Klasse
import com.sapuseven.untis.models.untis.masterdata.Room
import com.sapuseven.untis.models.untis.masterdata.SchoolYear
import com.sapuseven.untis.models.untis.masterdata.Subject
import com.sapuseven.untis.models.untis.masterdata.Teacher
import com.sapuseven.untis.models.untis.masterdata.TeachingMethod
import com.sapuseven.untis.models.untis.masterdata.TimeGrid
import kotlinx.serialization.Serializable

@Serializable
data class UntisMasterData(
		val timeStamp: Long = 0,
		val absenceReasons: List<AbsenceReason>?,
		val departments: List<Department>?,
		val duties: List<Duty>?,
		val eventReasons: List<EventReason>?,
		val eventReasonGroups: List<EventReasonGroup>?,
		val excuseStatuses: List<ExcuseStatus>?,
		val holidays: List<Holiday>?,
		val klassen: List<Klasse>,
		val rooms: List<Room>,
		val subjects: List<Subject>,
		val teachers: List<Teacher>,
		val teachingMethods: List<TeachingMethod>?,
		val schoolyears: List<SchoolYear>?,
		val timeGrid: TimeGrid?
)
