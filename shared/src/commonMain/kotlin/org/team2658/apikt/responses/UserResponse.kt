package org.team2658.apikt.responses

import kotlinx.serialization.Serializable
import org.team2658.emotion.attendance.UserAttendance

@Serializable
data class UserResponse(
    val _id: String,
    val firstname: String,
    val lastname: String,
    val username: String,
    val email: String,
    val phone: String?,
    val token: String? = null,
    val subteam: String? = null,
    val grade: Int? = null,
    val roles: Array<RoleResponse>? = null,
    val accountType: Int? = null, // isAdmin && isVerified are NOT supported. This client was developed for API v2.0.0 and later
    val attendance: Array<UserAttendance>
//    val accountUpdateVersion: Int? = null,
//    val socials: Array<String>? = null,
//    val parents: Array<User>? = null,
//    val attendance: Array<Attendance>? = null,
//    val children: Array<User>? = null,
//    val spouse: User? = null,
//    val donationAmounts: Array<Double>? = null,
//    val employer: Employer? = null,
)