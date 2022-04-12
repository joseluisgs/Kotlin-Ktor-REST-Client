package dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDTO (
    val email: String,
    val password: String
)

@Serializable
data class LoginResponseDTO (
    val token: String
)
