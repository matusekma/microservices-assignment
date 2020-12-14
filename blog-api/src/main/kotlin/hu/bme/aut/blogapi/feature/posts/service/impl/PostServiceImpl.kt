package hu.bme.aut.blogapi.feature.posts.service.impl

import hu.bme.aut.blogapi.exception.EntityNotFoundException
import hu.bme.aut.blogapi.feature.posts.dto.*
import hu.bme.aut.blogapi.feature.posts.service.PostService
import hu.bme.aut.blogapi.feature.posts.service.ProfanityFilterService
import hu.bme.aut.blogapi.repository.PostRepository
import hu.bme.aut.blogapi.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PostServiceImpl(val postRepository: PostRepository,
                      val userRepository: UserRepository,
                      val profanityFilterService: ProfanityFilterService) : PostService {

    private fun findPostByIdOrThrow(id: String) =
            postRepository.findById(id).orElseThrow { throw EntityNotFoundException("Post with id $id not found.") }

    override fun getPostById(id: String): PostResponse {
        return findPostByIdOrThrow(id).toPostResponse()
    }

    override fun findAllPostsByUserPaged(userId: String, isArchived: Boolean, pageable: Pageable): Page<PostResponse> {
        return postRepository.findAllByUserIdAndIsArchivedIs(userId, isArchived, pageable)
                .map { it.toPostResponse() }
    }

    override fun findAllPostsPaged(isArchived: Boolean, pageable: Pageable): Page<PostResponse> {
        return postRepository.findAllByIsArchivedIs(isArchived, pageable)
                .map { it.toPostResponse() }
    }

    override fun createPostForUser(userId: String, createPostRequest: CreatePostRequest): CreatePostResponse {
        if (!userRepository.existsById(userId)) {
            throw EntityNotFoundException("Post cannot be created because no user was found with the given id $userId")
        }
        val post = createPostRequest.toPost(userId).apply {
            content = profanityFilterService.filter(content);
            title = profanityFilterService.filter(title)
        }
        return postRepository.insert(post).toCreatePostResponse()
    }

    override fun deletePostById(id: String) {
        postRepository.deleteById(id)
    }

    override fun toggleIsArchivedForPost(id: String): PostResponse {
        val post = findPostByIdOrThrow(id)
        post.apply {
            isArchived = !isArchived
            updatedAt = LocalDateTime.now()
        }
        return postRepository.save(post).toPostResponse()
    }

    override fun updatePost(id: String, updatePostRequest: UpdatePostRequest): PostResponse {
        val post = findPostByIdOrThrow(id)
        post.apply {
            isArchived = updatePostRequest.isArchived ?: this.isArchived
            title = if (updatePostRequest.title == title) title else profanityFilterService.filter(updatePostRequest.title)
            content = if (updatePostRequest.title == content) content else profanityFilterService.filter(updatePostRequest.content)
            updatedAt = LocalDateTime.now()
        }
        return postRepository.save(post).toPostResponse()
    }
}