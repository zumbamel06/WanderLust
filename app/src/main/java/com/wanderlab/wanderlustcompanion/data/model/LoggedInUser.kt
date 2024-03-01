package com.wanderlab.wanderlustcompanion.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String,
    val fullName: String = "Full Name",
    val nickName: String = "Nick Name",
    val phoneNumber: String = "Phone Number",
    val email: String = "Email",
    val dob: String = "DOB",
    val gender: String = "Gender"
)