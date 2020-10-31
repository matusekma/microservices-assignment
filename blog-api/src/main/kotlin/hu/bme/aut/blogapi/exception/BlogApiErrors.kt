package hu.bme.aut.blogapi.exception

class BlogApiErrors {
    companion object ErrorCodes {
        const val ENTITY_NOT_FOUND_ERROR = "1000"
        const val MONGO_DB_ERROR = "1001"
    }
}