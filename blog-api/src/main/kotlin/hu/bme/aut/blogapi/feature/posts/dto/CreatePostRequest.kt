package hu.bme.aut.blogapi.feature.posts.dto

data class CreatePostRequest(val title: String,
                             val content: String)