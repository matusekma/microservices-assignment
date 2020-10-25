package hu.bme.aut.blogapi.repository

import hu.bme.aut.blogapi.domain.Post
import org.springframework.data.mongodb.repository.MongoRepository

interface PostRepository : MongoRepository<Post, String>

