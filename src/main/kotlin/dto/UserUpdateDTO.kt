package dto

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateDTO (
    var first_name: String,
    var last_name: String,
    var avatar: String?,
    var email: String?,
    var id: Int,
    var updatedAt: String
)