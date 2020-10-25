package hu.bme.aut.blogapi.feature.posts.dto

import hu.bme.aut.blogapi.domain.Post
import java.time.LocalDateTime

fun Post.toPostResponse(): PostResponse {
    return PostResponse(
            id = this.id!!,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt
    )
}

fun PostRequest.toPost(): Post {
    return Post(
            title = title,
            content = content,
            createdAt = LocalDateTime.now())
}