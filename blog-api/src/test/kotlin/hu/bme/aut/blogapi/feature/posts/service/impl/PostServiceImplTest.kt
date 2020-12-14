package hu.bme.aut.blogapi.feature.posts.service.impl

import hu.bme.aut.blogapi.domain.Post
import hu.bme.aut.blogapi.exception.EntityNotFoundException
import hu.bme.aut.blogapi.feature.posts.*
import hu.bme.aut.blogapi.feature.posts.dto.PostResponse
import hu.bme.aut.blogapi.feature.posts.dto.toPost
import hu.bme.aut.blogapi.feature.posts.service.PostService
import hu.bme.aut.blogapi.feature.posts.service.ProfanityFilterService
import hu.bme.aut.blogapi.repository.PostRepository
import hu.bme.aut.blogapi.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

class PostServiceTest {

    private lateinit var postService: PostService
    private lateinit var profanityFilterService: ProfanityFilterService
    private lateinit var postRepository: PostRepository
    private lateinit var userRepository: UserRepository

    @BeforeEach
    internal fun setUp() {
        postRepository = mock(PostRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        profanityFilterService = mock(ProfanityFilterServiceImpl::class.java)
        `when`(profanityFilterService.filter(anyString())).thenAnswer { it.arguments[0] }
        postService = PostServiceImpl(postRepository, userRepository, profanityFilterService)
    }

    @Test
    internal fun `test post returned by id successfully`() {
        val mockPost = getMockPost()
        `when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))

        val postResponse = postService.getPostById(mockId)

        verify(postRepository).findById(mockId)
        assertPostResponse(mockPost, postResponse)
    }

    @Test
    internal fun `test post delete`() {
        postService.deletePostById(mockId)

        verify(postRepository).deleteById(mockId)
    }

    @Test
    internal fun `test all posts returned by id paged`() {
        val mockPost = getMockPost()
        val mockPost1 = mockPost.copy(id = "124")
        val mockPage = PageImpl(listOf(mockPost, mockPost1))
        `when`(postRepository.findAllByIsArchivedIs(false, PageRequest.of(0, 2))).thenReturn(mockPage)

        val postResponsePage = postService.findAllPostsPaged(false, PageRequest.of(0, 2))

        verify(postRepository).findAllByIsArchivedIs(false, PageRequest.of(0, 2))
        assertEquals(mockPage.totalElements, postResponsePage.totalElements)
        assertEquals(mockPage.numberOfElements, postResponsePage.numberOfElements)
        mockPage.zip(postResponsePage).forEach { (expected, actual) -> assertPostResponse(expected, actual) }
    }

    @Test
    internal fun `test all posts returned by id paged per user`() {
        val mockPost = getMockPost()
        val mockPost1 = mockPost.copy(id = "124")
        val mockPage = PageImpl(listOf(mockPost, mockPost1))
        Mockito.`when`(postRepository.findAllByUserIdAndIsArchivedIs(mockId, false, PageRequest.of(0, 2))).thenReturn(mockPage)

        val postResponsePage = postService.findAllPostsByUserPaged(mockId, false, PageRequest.of(0, 2))

        verify(postRepository).findAllByUserIdAndIsArchivedIs(mockId, false, PageRequest.of(0, 2))
        assertEquals(mockPage.totalElements, postResponsePage.totalElements)
        assertEquals(mockPage.numberOfElements, postResponsePage.numberOfElements)
        mockPage.zip(postResponsePage).forEach { (expected, actual) -> assertPostResponse(expected, actual) }
    }

    @Test
    internal fun `test changing isArchived property of post`() {
        val mockPost = getMockPost()
        val originalPost = mockPost.copy()
        `when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))
        `when`(postRepository.save(any(Post::class.java))).thenAnswer { invocation -> invocation.getArgument(0) }

        val postResponse = postService.toggleIsArchivedForPost(mockId)

        verify(postRepository).save(mockPost)
        verify(postRepository).findById(mockId)
        assertNotEquals(originalPost.updatedAt, postResponse.updatedAt)
        assertNotEquals(originalPost.isArchived, postResponse.isArchived)
    }

    @Test
    internal fun `test updating post without modifying isArchived`() {
        val mockPost = getMockPost()
        val originalPost = mockPost.copy()
        val mockUpdatePostRequest = getMockUpdatePostRequestWithoutIsArchived()
        `when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))
        `when`(postRepository.save(any(Post::class.java))).thenAnswer { invocation -> invocation.getArgument(0) }

        val postResponse = postService.updatePost(mockId, mockUpdatePostRequest)

        verify(postRepository).save(mockPost)
        verify(postRepository).findById(mockId)
        assertNotEquals(originalPost.updatedAt, postResponse.updatedAt)
        assertEquals(originalPost.isArchived, postResponse.isArchived)
        assertEquals(mockUpdatePostRequest.content, postResponse.content)
        assertEquals(mockUpdatePostRequest.title, postResponse.title)
    }

    @Test
    internal fun `test updating post with modifying isArchived`() {
        val mockPost = getMockPost()
        val originalPost = mockPost.copy()
        val mockUpdatePostRequest = getMockUpdatePostRequest()
        `when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))
        `when`(postRepository.save(any(Post::class.java))).thenAnswer { invocation -> invocation.getArgument(0) }

        val postResponse = postService.updatePost(mockId, mockUpdatePostRequest)

        verify(postRepository).save(mockPost)
        verify(postRepository).findById(mockId)
        assertNotEquals(originalPost.updatedAt, postResponse.updatedAt)
        assertEquals(mockUpdatePostRequest.isArchived, postResponse.isArchived)
        assertEquals(mockUpdatePostRequest.content, postResponse.content)
        assertEquals(mockUpdatePostRequest.title, postResponse.title)
    }

    @Test
    internal fun `test post not found`() {
        `when`(postRepository.findById(mockId)).thenReturn(Optional.empty())

        val exception = assertThrows<EntityNotFoundException> { postService.getPostById(mockId) }

        assertEquals(exception.message, "Post with id $mockId not found.")
    }

    @Test
    internal fun `test post creation`() {
        val mockCreatePostRequest = getMockCreatePostRequest()
        val mockUserId = "456"
        val mockPostToBeInserted = mockCreatePostRequest.toPost(mockUserId)
        Mockito.`when`(userRepository.existsById(mockUserId)).thenReturn(true)
        Mockito.`when`(postRepository.insert(any(Post::class.java)))
                .thenReturn(mockPostToBeInserted.copy(id = mockId))

        val createPostResponse = postService.createPostForUser(mockUserId, mockCreatePostRequest)

        verify(postRepository).insert(any(Post::class.java))
        assertEquals(mockCreatePostRequest.title, createPostResponse.title)
        assertEquals(mockCreatePostRequest.content, createPostResponse.content)
        assertEquals(mockId, createPostResponse.id)
        assertEquals(mockUserId, createPostResponse.userId)
        assertEquals(false, createPostResponse.isArchived)
    }

    private fun assertPostResponse(expected: Post, actual: PostResponse) {
        assertEquals(expected.id, actual.id)
        assertEquals(expected.content, actual.content)
        assertEquals(expected.title, actual.title)
        assertEquals(expected.createdAt, actual.createdAt)
        assertEquals(expected.isArchived, actual.isArchived)
        assertEquals(expected.userId, actual.userId)
    }
}