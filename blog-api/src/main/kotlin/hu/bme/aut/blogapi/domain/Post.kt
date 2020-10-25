package hu.bme.aut.blogapi.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Post(@Id var id: String? = null,
                @Indexed var title: String,
                var content: String,
                var createdAt: LocalDateTime)