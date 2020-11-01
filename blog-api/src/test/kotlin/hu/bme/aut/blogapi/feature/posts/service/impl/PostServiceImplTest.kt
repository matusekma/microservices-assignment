package hu.bme.aut.blogapi.feature.posts.service.impl

import hu.bme.aut.blogapi.domain.Post
import hu.bme.aut.blogapi.exception.EntityNotFoundException
import hu.bme.aut.blogapi.feature.posts.dto.PostResponse
import hu.bme.aut.blogapi.feature.posts.getMockPost
import hu.bme.aut.blogapi.feature.posts.getMockUpdatePostRequest
import hu.bme.aut.blogapi.feature.posts.getMockUpdatePostRequestWithoutIsArchived
import hu.bme.aut.blogapi.feature.posts.mockId
import hu.bme.aut.blogapi.feature.posts.service.PostService
import hu.bme.aut.blogapi.repository.PostRepository
import hu.bme.aut.blogapi.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

class PostServiceTest {

    private lateinit var postService: PostService
    private lateinit var postRepository: PostRepository
    private lateinit var userRepository: UserRepository

    @BeforeEach
    internal fun setUp() {
        postRepository = mock(PostRepository::class.java)
        userRepository = mock(UserRepository::class.java)
        postService = PostServiceImpl(postRepository, userRepository)
    }

    @Test
    internal fun `test post returned by id successfully`() {
        val mockPost = getMockPost()
        Mockito.`when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))

        val postResponse = postService.getPostById(mockId)

        verify(postRepository).findById(mockId)
        assertPostResponse(mockPost, postResponse)
    }

    @Test
    internal fun `test all posts returned by id paged`() {
        val mockPost = getMockPost()
        val mockPost1 = mockPost.copy(id = "124")
        val mockPage = PageImpl(listOf(mockPost, mockPost1))
        Mockito.`when`(postRepository.findAllByIsArchivedIs(false, PageRequest.of(0, 2))).thenReturn(mockPage)

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
        Mockito.`when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))
        Mockito.`when`(postRepository.save(any(Post::class.java))).thenAnswer { invocation -> invocation.getArgument(0) }

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
        Mockito.`when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))
        Mockito.`when`(postRepository.save(any(Post::class.java))).thenAnswer { invocation -> invocation.getArgument(0) }

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
        Mockito.`when`(postRepository.findById(mockId)).thenReturn(Optional.of(mockPost))
        Mockito.`when`(postRepository.save(any(Post::class.java))).thenAnswer { invocation -> invocation.getArgument(0) }

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
        Mockito.`when`(postRepository.findById(mockId)).thenReturn(Optional.empty())

        val exception = assertThrows<EntityNotFoundException> { postService.getPostById(mockId) }

        assertEquals(exception.message, "Post with id $mockId not found.")
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