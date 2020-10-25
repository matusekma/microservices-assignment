package hu.bme.aut.blogapi.repository

import hu.bme.aut.blogapi.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {
}