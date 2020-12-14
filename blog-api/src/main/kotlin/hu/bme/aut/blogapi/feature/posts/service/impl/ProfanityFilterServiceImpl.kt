package hu.bme.aut.blogapi.feature.posts.service.impl

import hu.bme.aut.blogapi.feature.posts.dto.FilterRequest
import hu.bme.aut.blogapi.feature.posts.service.ProfanityFilterService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class ProfanityFilterServiceImpl(val restTemplate: RestTemplate,
                                 @Value("\${profanity-filter.baseUrl}")
                                 val basePath: String) : ProfanityFilterService {


    override fun filter(text: String): String {
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        val request = HttpEntity(FilterRequest(text), headers)
        return restTemplate.postForObject("$basePath/filter", request, String::class.java) ?: ""
    }
}