package hu.bme.aut.blogapi.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Post(@Id var id: String? = null,
                @Indexed(name = "post_title_index", unique = true) var title: String,
                @TextIndexed var content: String,
                var createdAt: LocalDateTime,
                var updatedAt: LocalDateTime,
                var isArchived: Boolean,
                var userId: String)