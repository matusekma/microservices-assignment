package hu.bme.aut.blogapi.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(@Id var id: String? = null,
                @Indexed var username: String,
                var email: String)