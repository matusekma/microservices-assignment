package hu.bme.aut.blogapi.feature.users.service.impl

import hu.bme.aut.blogapi.domain.User
import hu.bme.aut.blogapi.exception.EntityNotFoundException
import hu.bme.aut.blogapi.feature.users.dto.UserResponse
import hu.bme.aut.blogapi.feature.users.dto.toUser
import hu.bme.aut.blogapi.feature.users.dto.toUserResponse
import hu.bme.aut.blogapi.feature.users.getMockCreateUserRequest
import hu.bme.aut.blogapi.feature.users.getMockUpdateUserRequest
import hu.bme.aut.blogapi.feature.users.getMockUser
import hu.bme.aut.blogapi.feature.users.mockId
import hu.bme.aut.blogapi.feature.users.service.UserService
import hu.bme.aut.blogapi.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.data.domain.Sort
import java.util.*


class UserServiceImplTest {

    private lateinit var userService: UserService
    private lateinit var userRepository: UserRepository

    @BeforeEach
    internal fun setUp() {
        userRepository = Mockito.mock(UserRepository::class.java)
        userService = UserServiceImpl(userRepository)
    }

    @Test
    internal fun `test user returned by id successfully`() {
        val mockUser = getMockUser()
        Mockito.`when`(userRepository.findById(mockId)).thenReturn(Optional.of(mockUser))

        val userResponse = userService.findUserById(mockId)

        verify(userRepository).findById(mockId)
        assertUserResponse(mockUser, userResponse)
    }

    @Test
    internal fun `test user not found`() {
        Mockito.`when`(userRepository.findById(mockId)).thenReturn(Optional.empty())

        val exception = assertThrows<EntityNotFoundException> { userService.findUserById(mockId) }

        assertEquals(exception.message, "User with id ${hu.bme.aut.blogapi.feature.posts.mockId} not found.")
    }

    @Test
    internal fun `test user delete`() {
        userService.deleteUserById(mockId)

        verify(userRepository).deleteById(mockId)
    }

    @Test
    internal fun `test user creation`() {
        val mockCreateUserRequest = getMockCreateUserRequest()
        val mockUserToBeInserted = mockCreateUserRequest.toUser()
        Mockito.`when`(userRepository.insert(any(User::class.java)))
                .thenReturn(mockUserToBeInserted.copy(id = mockId))

        val userResponse = userService.createUser(mockCreateUserRequest)

        verify(userRepository).insert(any(User::class.java))
        assertEquals(mockCreateUserRequest.email, userResponse.email)
        assertEquals(mockCreateUserRequest.username, userResponse.username)
        assertEquals(mockId, userResponse.id)
    }

    @Test
    internal fun `test user update`() {
        val mockUpdateUserRequest = getMockUpdateUserRequest()
        val mockUser = getMockUser()
        val originalUser = mockUser.copy()
        Mockito.`when`(userRepository.findById(mockId)).thenReturn(Optional.of(mockUser))
        Mockito.`when`(userRepository.save(any(User::class.java))).thenAnswer { invocation -> invocation.getArgument(0) }

        val userResponse = userService.updateUser(mockId, mockUpdateUserRequest)

        verify(userRepository).save(mockUser)
        assertEquals(mockUpdateUserRequest.username, userResponse.username)
        assertEquals(originalUser.email, userResponse.email)
        assertEquals(originalUser.id, userResponse.id)
    }

    @Test
    internal fun `test all users returned`() {
        val mockUser = getMockUser()
        val mockUser1 = mockUser.copy(id = "124")
        Mockito.`when`(userRepository.findAll(Sort.by(Sort.Direction.DESC, "username")))
                .thenReturn(listOf(mockUser, mockUser1))

        val userResponseList = userService.findAllUsersSorted(Sort.by(Sort.Direction.DESC, "username"))

        verify(userRepository).findAll(Sort.by(Sort.Direction.DESC, "username"))
        assertEquals(2, userResponseList.size)
        assertEquals(mockUser.toUserResponse(), userResponseList[0])
        assertEquals(mockUser1.toUserResponse(), userResponseList[1])
    }

    private fun assertUserResponse(expected: User, actual: UserResponse) {
        assertEquals(expected.id, actual.id)
        assertEquals(expected.username, actual.username)
        assertEquals(expected.email, actual.email)
    }
}