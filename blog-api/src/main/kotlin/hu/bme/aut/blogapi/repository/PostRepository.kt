package hu.bme.aut.blogapi.repository

import hu.bme.aut.blogapi.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : MongoRepository<Post, String> {
    fun findAllByUserIdAndIsArchivedIs(userId: String, isArchived: Boolean, pageable: Pageable): Page<Post>
    fun findAllByIsArchivedIs(isArchived: Boolean, pageable: Pageable): Page<Post>
}

