package hu.bme.aut.blogapi.feature.posts.dto

data class UpdatePostRequest(
        val title: String,
        val content: String,
        val isArchived: Boolean?)

