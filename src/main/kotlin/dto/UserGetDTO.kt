package dto

import kotlinx.serialization.Serializable
import models.Support
import models.User

// Serializamos en JSON
@Serializable
data class UserDTO(
    val data: User,
    val support: Support
)