@file:Suppress("unused")

package com.buildconfig.internal.model

data class ModuleArtifactCoordinates constructor(
    val PUBLISH_GROUP_ID: String,
    val PUBLISH_ARTIFACT_ID: String,
    val PUBLISH_VERSION: String,
    val PUBLISH_ARTIFACT_DESC: String,
    val PUBLISH_ARTIFACT_WEBSITE: String
) {
    fun artifactCoordinate() = "$PUBLISH_GROUP_ID:$PUBLISH_ARTIFACT_ID:$PUBLISH_VERSION"
}