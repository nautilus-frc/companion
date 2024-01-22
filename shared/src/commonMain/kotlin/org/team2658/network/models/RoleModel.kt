package org.team2658.network.models

import kotlinx.serialization.Serializable

@Serializable
data class RoleModel(
    val name: String,
    val permissions: RolePermissionsModel
)