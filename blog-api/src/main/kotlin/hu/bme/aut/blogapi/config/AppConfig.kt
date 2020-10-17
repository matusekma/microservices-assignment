package hu.bme.aut.blogapi.config

import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory


@Configuration
class AppConfig {

    @Autowired
    fun mongoDatabaseFactory(mongo: MongoDatabaseFactory) {
        val db = mongo.getMongoDatabase("blog-api")
        val collection = db.getCollection("asd")
        //collection.insertOne(Document("asd", "asd"))
        println(collection.countDocuments())
    }

}
