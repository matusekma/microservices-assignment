package hu.bme.aut.blogapi.feature.users.service

interface UserService {
    fun existsById(id: Int): Boolean
}
