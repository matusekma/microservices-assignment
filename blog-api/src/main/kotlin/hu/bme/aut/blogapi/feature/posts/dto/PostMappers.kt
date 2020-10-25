package hu.bme.aut.blogapi.feature.posts.dto

import hu.bme.aut.blogapi.domain.Post
import hu.bme.aut.blogapi.domain.User
import java.time.LocalDateTime

fun Post.toCreatePostResponse(): CreatePostResponse {
    return CreatePostResponse(
            id = this.id!!,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt
    )
}

fun CreatePostRequest.toPost(user: User): Post {
    return Post(
            title = title,
            content = content,
            createdAt = LocalDateTime.now(),
            user = user
    )
}