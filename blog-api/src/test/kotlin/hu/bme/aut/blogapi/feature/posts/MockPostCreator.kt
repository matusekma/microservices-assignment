package hu.bme.aut.blogapi.feature.posts

import hu.bme.aut.blogapi.domain.Post
import hu.bme.aut.blogapi.feature.posts.dto.UpdatePostRequest
import java.time.LocalDateTime

const val mockId = "123"

fun getMockPost(): Post = Post(
        id = mockId,
        title = "Test title",
        content = "Test content",
        createdAt = LocalDateTime.of(2020, 1, 1, 12, 12),
        updatedAt = LocalDateTime.of(2020, 1, 1, 12, 30),
        isArchived = false,
        userId = mockId)

fun getMockUpdatePostRequestWithoutIsArchived(): UpdatePostRequest = UpdatePostRequest(
        title = "Updated title",
        content = "Updated content"
)

fun getMockUpdatePostRequest(): UpdatePostRequest = UpdatePostRequest(
        title = "Updated title",
        content = "Updated content",
        isArchived = false
)