package hu.bme.aut.blogapi.feature.posts.service.impl

import hu.bme.aut.blogapi.exception.EntityNotFoundException
import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.posts.dto.toCreatePostResponse
import hu.bme.aut.blogapi.feature.posts.service.PostService
import hu.bme.aut.blogapi.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostServiceImpl(val postRepository: PostRepository) : PostService {

    override fun getPostById(postId: String): CreatePostResponse {
        val post = postRepository.findById(postId).orElseThrow { throw EntityNotFoundException("Post not found.") }
        return post.toCreatePostResponse()
    }
}