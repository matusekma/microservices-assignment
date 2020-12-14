package hu.bme.aut.blogapi.feature.posts.service

interface ProfanityFilterService {
    fun filter(text: String): String
}