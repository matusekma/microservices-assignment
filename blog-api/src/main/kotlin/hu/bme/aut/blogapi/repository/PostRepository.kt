package hu.bme.aut.blogapi.repository

import hu.bme.aut.blogapi.domain.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : MongoRepository<Post, String>

