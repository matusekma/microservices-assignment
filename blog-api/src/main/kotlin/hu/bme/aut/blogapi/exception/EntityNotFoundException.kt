package hu.bme.aut.blogapi.exception

import java.lang.RuntimeException

class EntityNotFoundException(message: String) : RuntimeException(message)