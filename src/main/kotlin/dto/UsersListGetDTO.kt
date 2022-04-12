package dto

import kotlinx.serialization.Serializable
import models.Support
import models.User


@Serializable
data class UserListDTO(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>,
    val support: Support
)