package zjtech.smf.config

import com.mongodb.Mongo
import com.mongodb.WriteConcern
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.authentication.UserCredentials
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoDbFactory
import zjtech.smf.common.constants.UriConst.Companion.MONGODB_HOST
import zjtech.smf.common.constants.UriConst.Companion.MONGODB_NAME


//@Configuration
//@Lazy
class MongodbConfig {

    //    @Lazy
//    @Bean @Throws(Exception::class)
    fun mongoDbFactory(): MongoDbFactory {
        val userCredentials = UserCredentials("root", "root")
        return SimpleMongoDbFactory(getMongo(), MONGODB_NAME, userCredentials)
    }

    //    @Bean
    fun getMongo(): Mongo {
        /*
         * Using MongoDB driver version 3 requires to set the WriteConcern to ACKNOWLEDGED.
         * Otherwise OptimisticLockingFailureException can be silently swallowed.
         *
         * Write concern describes the guarantee that MongoDB provides when reporting on the success of a write operation.
         * The strength of the write concerns determine the level of guarantee it control the write behavior for with various options,
         * as well as exception raising on error conditions. The available write concerns are..

         * (Unacknowledged) -1 =No exceptions are raised, even for network issues

         * (Acknowledged) 0 = Exceptions are raised for network issues, but not server errors

         * (Journaled) 1 = Exceptions are raised for network issues, and server errors; waits on a server for the write operation

         * (Replica acknowledged) 2 = Exceptions are raised for network issues, and server errors; waits for at least 2 servers for the write operation

         * 当update一个对象时，需要携带对应的version版本号，如果版本号不一致会抛出异常提示用户刷新再试
         *
         */
        var mongo = Mongo(MONGODB_HOST, 5801)
        mongo.writeConcern = WriteConcern.ACKNOWLEDGED
        return mongo
    }

    //    @Bean @Throws(Exception::class)
    fun mongoTemplate(): MongoTemplate = MongoTemplate(mongoDbFactory())

}
