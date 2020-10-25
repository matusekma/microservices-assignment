package hu.bme.aut.blogapi.feature.posts.dto

import java.time.LocalDateTime

data class CreatePostResponse(val id: String,
                              val title: String,
                              val content: String,
                              val createdAt: LocalDateTime)